package br.com.marcoshssilva.botstoremessages.rabbit.queues;

import br.com.marcoshssilva.botstoremessages.domain.services.IContactMessageService;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageData;
import br.com.marcoshssilva.botstoremessages.domain.services.models.ContactMessageNew;
import br.com.marcoshssilva.botstoremessages.rabbit.models.RegisterContactMessageModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(prefix = "rabbit", name = "enabled", havingValue = "true")
@lombok.RequiredArgsConstructor
@lombok.extern.slf4j.Slf4j
public class QueueRegisterContactMessage {
    private final ObjectMapper objectMapper;
    private final IContactMessageService iContactMessageService;

    @RabbitListener(queues = {"${rabbit.queues.register-contact-message:notify.contact-me}"})
    public void onReceiveMessage(@Payload Message message) {
        try {
            final RegisterContactMessageModel messageModel = objectMapper.readValue(message.getBody(), RegisterContactMessageModel.class);
            final Optional<ContactMessageData> messageData = iContactMessageService.registerNewContactMessage(new ContactMessageNew(messageModel.getName(), messageModel.getMail(), messageModel.getMessage()));
            messageData.ifPresent(contactMessageData -> log.info("Successfully registered new ContactMessage with ID {}", contactMessageData.id()));
        } catch (Exception e) {
            log.error("Fatal error on process message, MESSAGE_ERROR: {}, PAYLOAD: [{}]", e.getMessage(), message);
        }

    }
}
