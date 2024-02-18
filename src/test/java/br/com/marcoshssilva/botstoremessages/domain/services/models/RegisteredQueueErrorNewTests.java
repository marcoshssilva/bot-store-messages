package br.com.marcoshssilva.botstoremessages.domain.services.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RegisteredQueueErrorNewTests {

    @DisplayName("must validate equals() and hashCode() methods")
    @Test
    void testEqualsAndHashCode() {
        RegisteredQueueErrorNew error1 = new RegisteredQueueErrorNew("Error 1", "data1".getBytes());
        RegisteredQueueErrorNew error2 = new RegisteredQueueErrorNew("Error 1", "data1".getBytes());
        RegisteredQueueErrorNew error3 = new RegisteredQueueErrorNew("Error 2", "data2".getBytes());
        RegisteredQueueErrorNew error4 = new RegisteredQueueErrorNew("Error 2", "data1".getBytes());

        // Test equals method
        assertEquals(error1, error1);
        assertEquals(error1, error2);
        assertNotEquals(error1, error3);
        assertNotEquals(null, error1);
        assertNotEquals(error2, error4);

        // Test hashCode method
        assertEquals(error1.hashCode(), error2.hashCode());
        assertNotEquals(error1.hashCode(), error3.hashCode());
    }

    @DisplayName("must call toString() with expected string")
    @Test
    void testToString() {
        RegisteredQueueErrorNew error = new RegisteredQueueErrorNew("Error message", "Some data".getBytes());
        String expectedToString = "RegisteredQueueErrorNew(errorMessage=Error message, errorData=[83, 111, 109, 101, 32, 100, 97, 116, 97])";

        assertEquals(expectedToString, error.toString());
    }
}
