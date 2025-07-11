package br.com.marcoshssilva.botstoremessages.domain.repositories;

import br.com.marcoshssilva.botstoremessages.domain.entities.ContactMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Testcontainers
class ContactMessageRepositoryTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:8.0"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ContactMessageRepository repository;

    @DisplayName("must save list of ContactMessage and get then with generated IDs")
    @Test
    @Order(1)
    void testSaveAllAndGet() {
        // Save
        final ContactMessage cm1 = ContactMessage.builder()
                .name("Hello1")
                .email("heelo1@mail.com")
                .message("hi")
                .build();

        final ContactMessage cm2 = ContactMessage.builder()
                .name("Hello2")
                .email("heelo2@mail.com")
                .message("hi")
                .build();

        final ContactMessage cm3 = ContactMessage.builder()
                .name("Hello3")
                .email("heelo3@mail.com")
                .message("hi")
                .build();

        List<ContactMessage> contactMessages = List.of(cm1, cm2, cm3);

        // force delete all to fix tests in CI
        repository.deleteAll();
        repository.saveAll(contactMessages);

        // Find all
        final List<ContactMessage> allContactMessages = repository.findAll();
        assertEquals(3, allContactMessages.size());
        assertTrue(allContactMessages.stream().allMatch(item -> Objects.nonNull(item.getId())));
    }

    @DisplayName("must save object of ContactMessage and delete it on database")
    @Test
    @Order(2)
    void testSaveFindByIdDelete() {
        // Save
        ContactMessage savedMessage = repository.save(ContactMessage.builder()
                .name("John")
                .email("john@example.com")
                .message("Hello")
                .build());

        // Find By Id
        ContactMessage foundMessage = repository.findById(savedMessage.getId()).orElse(null);
        assertNotNull(foundMessage);
        assertEquals("John", foundMessage.getName());
        assertEquals("john@example.com", foundMessage.getEmail());
        assertEquals("Hello", foundMessage.getMessage());

        // Delete
        repository.deleteById(savedMessage.getId());

        // Check if deleted
        assertFalse(repository.existsById(savedMessage.getId()));
    }

    @DisplayName("must save object of ContactMessage and update it on database")
    @Test
    @Order(3)
    void testUpdate() {
        // Save
        ContactMessage savedMessage = repository.save(ContactMessage.builder()
                .name("John")
                .email("john@example.com")
                .message("Hello")
                .build());

        // Modify
        savedMessage.setName("Jane");
        savedMessage.setEmail("jane@example.com");
        savedMessage.setMessage("Hi");

        // Update
        repository.save(savedMessage);

        // Find By Id and Check if updated
        ContactMessage updatedMessage = repository.findById(savedMessage.getId()).orElse(null);
        assertNotNull(updatedMessage);
        assertEquals("Jane", updatedMessage.getName());
        assertEquals("jane@example.com", updatedMessage.getEmail());
        assertEquals("Hi", updatedMessage.getMessage());
    }
}
