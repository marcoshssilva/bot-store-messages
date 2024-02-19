package br.com.marcoshssilva.botstoremessages.domain.services;

import br.com.marcoshssilva.botstoremessages.domain.services.exceptions.RegisterQueueErrorCannotBeCreatedException;
import br.com.marcoshssilva.botstoremessages.domain.services.models.RegisteredQueueErrorData;
import br.com.marcoshssilva.botstoremessages.domain.services.models.RegisteredQueueErrorNew;

public interface IRegisterQueueErrorService {
    RegisteredQueueErrorData registerQueueError(RegisteredQueueErrorNew newQueueError) throws RegisterQueueErrorCannotBeCreatedException;
}
