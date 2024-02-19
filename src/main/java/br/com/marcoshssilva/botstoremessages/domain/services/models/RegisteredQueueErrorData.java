package br.com.marcoshssilva.botstoremessages.domain.services.models;

import java.util.Date;

public record RegisteredQueueErrorData(String id, String errorMessage, String errorData, Date errorTimestamp) {
}
