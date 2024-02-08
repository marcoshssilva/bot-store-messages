package br.com.marcoshssilva.botstoremessages.domain.services.impl;

import br.com.marcoshssilva.botstoremessages.domain.entities.ContactMessage;
import br.com.marcoshssilva.botstoremessages.domain.repositories.ContactMessageRepository;
import br.com.marcoshssilva.botstoremessages.domain.services.IContactMessageService;
import br.com.marcoshssilva.botstoremessages.domain.services.exceptions.ContactMessageCannotBeCreatedException;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageData;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageNew;
import org.springframework.stereotype.Service;

import java.util.Optional;

@lombok.RequiredArgsConstructor
@Service
public class ContactMessageServiceImpl implements IContactMessageService {

    private final ContactMessageRepository repository;

    @Override
    public Optional<ContactMessageData> registerNewContactMessage(ContactMessageNew contactMessageNew) throws ContactMessageCannotBeCreatedException {
        try {
            final ContactMessage build = ContactMessage.builder().message(contactMessageNew.message()).email(contactMessageNew.email()).name(contactMessageNew.name()).build();
            final ContactMessage saved = repository.save(build);
            return Optional.of(new ContactMessageData(saved.getId(), saved.getName(), saved.getEmail(), saved.getMessage()));
        } catch (Exception e) {
            throw new ContactMessageCannotBeCreatedException("Cannot save new ContactMessage: " + e.getMessage(), e);
        }
    }
}
