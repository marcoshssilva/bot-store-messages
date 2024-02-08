package br.com.marcoshssilva.botstoremessages.domain.services;

import br.com.marcoshssilva.botstoremessages.domain.entities.ContactMessage;
import br.com.marcoshssilva.botstoremessages.domain.repositories.ContactMessageRepository;
import br.com.marcoshssilva.botstoremessages.domain.services.exceptions.ContactMessageCannotBeCreatedException;
import br.com.marcoshssilva.botstoremessages.domain.services.impl.ContactMessageServiceImpl;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageData;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageNew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class IContactMessageServiceTests {
    private ContactMessageServiceImpl contactMessageService;

    @Autowired
    private ContactMessageRepository repository;

    @BeforeEach
    void setup() {
        this.contactMessageService = new ContactMessageServiceImpl(repository);
    }

    @DisplayName("must register a new ContactMessage, store it on database and return instance of ContactMessageData")
    @Test
    void testRegisterNewContactMessage_Success() throws ContactMessageCannotBeCreatedException {
        // Mocking input data
        ContactMessageNew contactMessageNew = new ContactMessageNew("John", "john@example.com", "Hello");

        // Testing service method
        Optional<ContactMessageData> result = contactMessageService.registerNewContactMessage(contactMessageNew);

        // Assertions
        assertTrue(result.isPresent());
        assertNotEquals(null, result.get().id());

        assertEquals("John", result.get().name());
        assertEquals("john@example.com", result.get().email());
        assertEquals("Hello", result.get().message());

        final Optional<ContactMessage> message = repository.findById(result.get().id());

        assertTrue(message.isPresent());
        assertEquals("John", message.get().getName());
        assertEquals("john@example.com", message.get().getEmail());
        assertEquals("Hello", message.get().getMessage());
    }

}
