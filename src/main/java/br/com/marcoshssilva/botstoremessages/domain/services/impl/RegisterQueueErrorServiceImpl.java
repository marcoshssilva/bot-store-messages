package br.com.marcoshssilva.botstoremessages.domain.services.impl;

import br.com.marcoshssilva.botstoremessages.domain.entities.RegisteredQueueErrors;
import br.com.marcoshssilva.botstoremessages.domain.repositories.RegisteredQueueErrorsRepository;
import br.com.marcoshssilva.botstoremessages.domain.services.IRegisterQueueErrorService;
import br.com.marcoshssilva.botstoremessages.domain.services.exceptions.RegisterQueueErrorCannotBeCreatedException;
import br.com.marcoshssilva.botstoremessages.domain.services.models.RegisteredQueueErrorData;
import br.com.marcoshssilva.botstoremessages.domain.services.models.RegisteredQueueErrorNew;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@lombok.RequiredArgsConstructor
@Service
public class RegisterQueueErrorServiceImpl implements IRegisterQueueErrorService {
    public static final Base64.Encoder encoder = Base64.getEncoder();
    public static final String MSG_ERROR_CANNOT_BE_CREATED_EXCEPTION = "Cannot register queue error, because: %s";

    private final RegisteredQueueErrorsRepository registeredQueueErrorsRepository;

    @Override
    public RegisteredQueueErrorData registerQueueError(RegisteredQueueErrorNew newQueueError) throws RegisterQueueErrorCannotBeCreatedException {
        try {
            final String dataEncoded = encoder.encodeToString(newQueueError.errorData());
            final RegisteredQueueErrors saved = registeredQueueErrorsRepository.save(RegisteredQueueErrors.builder()
                    .errorTimestamp(new Date())
                    .errorMessage(newQueueError.errorMessage())
                    .data(dataEncoded)
                    .build());

            return new RegisteredQueueErrorData(saved.getId(), saved.getErrorMessage(), saved.getData(), saved.getErrorTimestamp());
        } catch (Exception e) {
            throw new RegisterQueueErrorCannotBeCreatedException(String.format(MSG_ERROR_CANNOT_BE_CREATED_EXCEPTION, e.getMessage()), e);
        }

    }
}
