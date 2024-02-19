package br.com.marcoshssilva.botstoremessages.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter

@Document(value = "registered_queues_errors")
public class RegisteredQueueErrors {
    @Id
    private String id;
    private String data;
    private String errorMessage;
    private Date errorTimestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredQueueErrors that = (RegisteredQueueErrors) o;
        return Objects.equals(id, that.id) && Objects.equals(data, that.data) && Objects.equals(errorMessage, that.errorMessage) && Objects.equals(errorTimestamp, that.errorTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, errorMessage, errorTimestamp);
    }
}
