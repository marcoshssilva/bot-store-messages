package br.com.marcoshssilva.botstoremessages.domain.services;

import br.com.marcoshssilva.botstoremessages.domain.entities.RegisteredQueueErrors;
import br.com.marcoshssilva.botstoremessages.domain.repositories.RegisteredQueueErrorsRepository;
import br.com.marcoshssilva.botstoremessages.domain.services.exceptions.RegisterQueueErrorCannotBeCreatedException;
import br.com.marcoshssilva.botstoremessages.domain.services.impl.RegisterQueueErrorServiceImpl;
import br.com.marcoshssilva.botstoremessages.domain.services.models.RegisteredQueueErrorData;
import br.com.marcoshssilva.botstoremessages.domain.services.models.RegisteredQueueErrorNew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IRegisterQueueErrorServiceTests {

    @Mock
    private RegisteredQueueErrorsRepository repository;

    @InjectMocks
    private RegisterQueueErrorServiceImpl errorService;

    @DisplayName("must register RegisteredQueueErrorData on database and return it")
    @Test
    void testRegisterQueueError_Success() throws RegisterQueueErrorCannotBeCreatedException {
        // Mocking input data
        byte[] errorData = "Some error data".getBytes();
        String errorDataAsBase64 = Base64.getEncoder().encodeToString(errorData);
        String errorMessage = "Error message";
        Date errorTimestamp = new Date();

        // Mocking repository behavior
        RegisteredQueueErrors savedError = new RegisteredQueueErrors("1", errorDataAsBase64, errorMessage, errorTimestamp);
        when(repository.save(any(RegisteredQueueErrors.class))).thenReturn(savedError);

        // Testing service method
        RegisteredQueueErrorData result = errorService.registerQueueError(new RegisteredQueueErrorNew(errorMessage, errorData));

        // Assertions
        assertNotNull(result);
        assertEquals("1", result.id());
        assertEquals(errorMessage, result.errorMessage());
        assertEquals(errorDataAsBase64, result.errorData());
        assertEquals(errorTimestamp, result.errorTimestamp());
    }

    @DisplayName("must register RegisteredQueueErrors and given error on connect to database")
    @Test
    void testRegisterQueueError_FailureRepository() {
        // Mocking input data
        byte[] errorData = "Some error data".getBytes();
        String errorMessage = "Error message";

        // Mocking repository behavior
        when(repository.save(any(RegisteredQueueErrors.class))).thenThrow(new DataIntegrityViolationException("Cannot save"));

        // Testing service method
        assertThrows(RegisterQueueErrorCannotBeCreatedException.class, () -> {
            errorService.registerQueueError(new RegisteredQueueErrorNew(errorMessage, errorData));
        });
    }


}
