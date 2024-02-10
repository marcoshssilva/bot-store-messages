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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class IContactMessageServiceTests {

    @InjectMocks
    private ContactMessageServiceImpl contactMessageService;

    @Mock
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
        ContactMessage contactMessage = ContactMessage.builder().id("1212121212").name("John").email("john@example.com").message("Hello").build();

        // Mock methods
        when(repository.save(any())).thenReturn(contactMessage);
        // Testing service method
        Optional<ContactMessageData> result = contactMessageService.registerNewContactMessage(contactMessageNew);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals("1212121212", result.get().id());
        assertEquals("John", result.get().name());
        assertEquals("john@example.com", result.get().email());
        assertEquals("Hello", result.get().message());

        // check that repository save must be called
        verify(repository, times(1)).save(any());
    }

    @DisplayName("must throw a ContactMessageCannotBeCreatedException when try save in database and gets error")
    @Test
    void testRegisterNewContactMessage_FailedBecause_RepositoryFails() {
        // Mocking input data
        ContactMessageNew contactMessageNew = new ContactMessageNew("John", "john@example.com", "Hello");
        // Mock methods
        when(repository.save(any())).thenThrow(new DataIntegrityViolationException("Database is OFFLINE"));
        // Testing service method
        assertThrows(ContactMessageCannotBeCreatedException.class, () -> contactMessageService.registerNewContactMessage(contactMessageNew));
    }
}
