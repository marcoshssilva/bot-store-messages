package br.com.marcoshssilva.botstoremessages.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter

@Document(value = "contact_messages")
public class ContactMessage {

    @Id
    private String id;
    private String name;
    private String email;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactMessage message1 = (ContactMessage) o;
        return Objects.equals(id, message1.id) && Objects.equals(name, message1.name) && Objects.equals(email, message1.email) && Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, message);
    }
}
