package br.com.marcoshssilva.botstoremessages.rabbit.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterContactMessageModelTests {

    @DisplayName("must call Getters and Setters")
    @Test
    void testGetterSetter() {
        RegisterContactMessageModel model = new RegisterContactMessageModel();
        model.setName("John");
        model.setMail("john@example.com");
        model.setMessage("Hello");

        assertEquals("John", model.getName());
        assertEquals("john@example.com", model.getMail());
        assertEquals("Hello", model.getMessage());
    }

    @DisplayName("must call toString() and get object data as String")
    @Test
    void testToString() {
        RegisterContactMessageModel model = new RegisterContactMessageModel();
        model.setName("John");
        model.setMail("john@example.com");
        model.setMessage("Hello");

        String expectedToString = "RegisterContactMessageModel(name=John, mail=john@example.com, message=Hello)";
        assertEquals(expectedToString, model.toString());
    }
}
