package br.com.marcoshssilva.botstoremessages.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
class ContactMessageTests {

    @DisplayName("must validate that setters and getters are OK")
    @Test
    void testGetterSetter() {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setId("1");
        contactMessage.setName("John");
        contactMessage.setEmail("john@example.com");
        contactMessage.setMessage("Hello");

        assertEquals("1", contactMessage.getId());
        assertEquals("John", contactMessage.getName());
        assertEquals("john@example.com", contactMessage.getEmail());
        assertEquals("Hello", contactMessage.getMessage());
    }

    @DisplayName("must validate that builder are OK and hashCode and equals are OK")
    @Test
    void testEqualsAndHashCode() {
        ContactMessage message1 = ContactMessage.builder()
                .id("1")
                .name("John")
                .email("john@example.com")
                .message("Hello")
                .build();
        ContactMessage message2 = ContactMessage.builder()
                .id("1")
                .name("John")
                .email("john@example.com")
                .message("Hello")
                .build();
        ContactMessage message3 = ContactMessage.builder()
                .id("2")
                .name("Jane")
                .email("jane@example.com")
                .message("Hi")
                .build();

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());

        assertNotEquals(message1, message3);
        assertNotEquals(message1.hashCode(), message3.hashCode());
    }

}
