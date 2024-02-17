package br.com.marcoshssilva.botstoremessages.rabbit.queues;

import br.com.marcoshssilva.botstoremessages.domain.entities.ContactMessage;
import br.com.marcoshssilva.botstoremessages.domain.entities.RegisteredQueueErrors;
import br.com.marcoshssilva.botstoremessages.domain.repositories.ContactMessageRepository;
import br.com.marcoshssilva.botstoremessages.domain.repositories.RegisteredQueueErrorsRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QueueRegisterContactMessageTest {

    @Container
    static RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"));

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    QueueRegisterContactMessage queueRegisterContactMessage;

    @Autowired
    RegisteredQueueErrorsRepository registeredQueueErrorsRepository;

    @Autowired
    ContactMessageRepository contactMessageRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.username", container::getAdminUsername);
        registry.add("spring.rabbitmq.password", container::getAdminPassword);
        registry.add("spring.rabbitmq.host", container::getHost);
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
    }

    @BeforeAll
    static void configure() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // Set the connection details to the RabbitMQ container
        factory.setHost(container.getHost());
        factory.setPort(container.getFirstMappedPort());
        factory.setUsername(container.getAdminUsername());
        factory.setPassword(container.getAdminPassword());

        try (Connection connection = factory.newConnection()) {
            // create queue before tests
            final Channel channel = connection.createChannel();
            channel.queueDeclare("notify.contact-me", false, false, false, null);
        }

    }

    @DisplayName("must send and receive messages from queue 'notify.contact-me' and process if error and if success")
    @Test
    void testOnReceiveMessage() throws InterruptedException {
        rabbitTemplate.send("notify.contact-me", new Message("{ \"name\": \"John Doe\", \"mail\":\"john.doe@mail.com\", \"message\":\"Hello\"}".getBytes(StandardCharsets.UTF_8)));
        rabbitTemplate.send("notify.contact-me", new Message("{ \"name\": \"Mary Doe\", \"mail\":\"mary.doe@mail.com\", \"message\":\"Hello\"}".getBytes(StandardCharsets.UTF_8)));
        rabbitTemplate.send("notify.contact-me", new Message("{ \"name\": \"Kane Doe\", \"mail\":\"kane.doe@mail.com\", \"message\":\"Hello\"}".getBytes(StandardCharsets.UTF_8)));
        rabbitTemplate.send("notify.contact-me", new Message("Corrupted data".getBytes(StandardCharsets.UTF_8)));
        // fix if exists another data
        registeredQueueErrorsRepository.deleteAll();
        contactMessageRepository.deleteAll();

        // sleep to await process queues
        Thread.sleep(3000);

        // fetch data
        final List<RegisteredQueueErrors> registeredQueueErrorsRepositoryAll = registeredQueueErrorsRepository.findAll();
        final List<ContactMessage> contactMessageRepositoryAll = contactMessageRepository.findAll();

        // assertions
        assertEquals(3, contactMessageRepositoryAll.size());
        assertEquals(1, registeredQueueErrorsRepositoryAll.size());
    }
}