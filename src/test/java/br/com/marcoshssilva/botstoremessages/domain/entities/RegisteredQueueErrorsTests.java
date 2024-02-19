package br.com.marcoshssilva.botstoremessages.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RegisteredQueueErrorsTests {
    @DisplayName("must validate that setters and getters are OK")
    @Test
    void testGetterSetter() {
        final Date errorTimestamp = new Date();
        final String someError = "some error";
        final String id = "1";
        final String someData = "some data";

        RegisteredQueueErrors registeredQueueErrors = new RegisteredQueueErrors();

        registeredQueueErrors.setId(id);
        registeredQueueErrors.setErrorMessage(someError);
        registeredQueueErrors.setErrorTimestamp(errorTimestamp);
        registeredQueueErrors.setData(someData);

        assertEquals(id, registeredQueueErrors.getId());
        assertEquals(someData, registeredQueueErrors.getData());
        assertEquals(someError, registeredQueueErrors.getErrorMessage());
        assertEquals(errorTimestamp, registeredQueueErrors.getErrorTimestamp());
    }

    @DisplayName("must validate that builder are OK and hashCode and equals are OK")
    @Test
    void testEqualsAndHashCode() {
        final Date errorTimestamp = new Date();
        RegisteredQueueErrors queueErrors1 = RegisteredQueueErrors.builder()
                .id("some id")
                .data("some data")
                .errorMessage("some error")
                .errorTimestamp(errorTimestamp)
                .build();

        RegisteredQueueErrors queueErrors2 = RegisteredQueueErrors.builder()
                .id("some id")
                .data("some data")
                .errorMessage("some error")
                .errorTimestamp(errorTimestamp)
                .build();

        RegisteredQueueErrors queueErrors3 = RegisteredQueueErrors.builder()
                .id("some other id")
                .data("some other data")
                .errorMessage("some other error")
                .errorTimestamp(errorTimestamp)
                .build();

        assertEquals(queueErrors1, queueErrors1);
        assertEquals(queueErrors1.hashCode(), queueErrors1.hashCode());
        assertEquals(queueErrors1, queueErrors2);
        assertEquals(queueErrors1.hashCode(), queueErrors2.hashCode());

        assertNotEquals(queueErrors1, queueErrors3);
        assertNotEquals(queueErrors1.hashCode(), queueErrors3.hashCode());
    }
}
