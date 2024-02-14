package br.com.marcoshssilva.botstoremessages.domain.repositories;

import br.com.marcoshssilva.botstoremessages.domain.entities.RegisteredQueueErrors;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredQueueErrorsRepository extends MongoRepository<RegisteredQueueErrors, String> {
}
