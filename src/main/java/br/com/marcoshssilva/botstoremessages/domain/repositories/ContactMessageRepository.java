package br.com.marcoshssilva.botstoremessages.domain.repositories;

import br.com.marcoshssilva.botstoremessages.domain.entities.ContactMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends MongoRepository<ContactMessage, String> {
}
