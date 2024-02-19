package br.com.marcoshssilva.botstoremessages.domain.services;

import br.com.marcoshssilva.botstoremessages.domain.services.exceptions.ContactMessageCannotBeCreatedException;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageData;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageNew;

import java.util.Optional;

public interface IContactMessageService {
    Optional<ContactMessageData> registerNewContactMessage(ContactMessageNew contactMessageNew) throws ContactMessageCannotBeCreatedException;
}
