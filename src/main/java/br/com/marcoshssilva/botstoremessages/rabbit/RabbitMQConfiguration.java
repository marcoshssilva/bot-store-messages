package br.com.marcoshssilva.botstoremessages.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    /**
     * Get instance of AmqpTemplate to perform operations on AMQP Server as send/receive messages by
     * queues or channels
     *
     * @param connectionFactory FactoryBuilder that instance connection with AMQP Server
     * @return org.springframework.amqp.core.RabbitTemplate.class
     */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // config to send any payload as Json, when producer mode
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    /**
     * Get instance to convert payload received by queue as JSON
     * @return org.springframework.amqp.support.converter.Jackson2JsonMessageConverter.class
     */
    @Bean
    public MessageConverter converter() {
        return new SimpleMessageConverter();
    }

}
