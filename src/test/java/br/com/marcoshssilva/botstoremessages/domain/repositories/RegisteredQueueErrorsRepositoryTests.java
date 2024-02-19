package br.com.marcoshssilva.botstoremessages.domain.repositories;

import br.com.marcoshssilva.botstoremessages.domain.entities.RegisteredQueueErrors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class RegisteredQueueErrorsRepositoryTests {
    @Autowired
    private RegisteredQueueErrorsRepository repository;

    @DisplayName("must save object of RegisteredQueueErrors on database with identity")
    @Test
    void testSave() {
        repository.deleteAll();
        // Mocking input data
        RegisteredQueueErrors error = RegisteredQueueErrors.builder()
                .data("Some data")
                .errorMessage("Error message")
                .errorTimestamp(new Date())
                .build();

        // Testing save method
        RegisteredQueueErrors savedError = repository.save(error);

        // Assertions
        assertNotNull(savedError);
        assertNotNull(savedError.getId()); // Assuming id is auto-generated
        assertEquals("Some data", savedError.getData());
        assertEquals("Error message", savedError.getErrorMessage());
    }

    @DisplayName("must save many objects of RegisteredQueueErrors on database with identity")
    @Test
    void testSaveAll() {
        // Mocking input data
        RegisteredQueueErrors error1 = RegisteredQueueErrors.builder()
                .data("Data 1")
                .errorMessage("Error message 1")
                .errorTimestamp(new Date())
                .build();

        RegisteredQueueErrors error2 = RegisteredQueueErrors.builder()
                .data("Data 2")
                .errorMessage("Error message 2")
                .errorTimestamp(new Date())
                .build();

        List<RegisteredQueueErrors> errors = List.of(error1, error2);

        // Testing saveAll method
        repository.deleteAll();
        List<RegisteredQueueErrors> savedErrors = repository.saveAll(errors);

        // Assertions
        assertNotNull(savedErrors);
        assertEquals(2, savedErrors.size());
        assertNotNull(savedErrors.get(0).getId()); // Assuming id is auto-generated
        assertNotNull(savedErrors.get(1).getId()); // Assuming id is auto-generated
    }

    @DisplayName("must save object of RegisteredQueueErrors on database with identity and delete it")
    @Test
    void testDelete() {
        repository.deleteAll();
        // Mocking input data
        RegisteredQueueErrors error = RegisteredQueueErrors.builder()
                .data("Some data")
                .errorMessage("Error message")
                .errorTimestamp(new Date())
                .build();
        RegisteredQueueErrors savedError = repository.save(error);

        // Testing delete method
        repository.delete(savedError);

        // Assertions
        assertEquals(0, repository.count());
    }

    @DisplayName("must save object of RegisteredQueueErrors on database with identity and update it")
    @Test
    void testUpdate() {
        // Mocking input data
        RegisteredQueueErrors error = RegisteredQueueErrors.builder()
                .data("Some data")
                .errorMessage("Error message")
                .errorTimestamp(new Date())
                .build();
        RegisteredQueueErrors savedError = repository.save(error);

        // Modify
        savedError.setData("Updated data");

        // Testing update method
        RegisteredQueueErrors updatedError = repository.save(savedError);

        // Assertions
        assertEquals(savedError.getId(), updatedError.getId());
        assertEquals("Updated data", updatedError.getData());
    }
}
