package br.com.marcoshssilva.botstoremessages.domain.services.exceptions;

public class RegisterQueueErrorCannotBeCreatedException extends Exception{
    public RegisterQueueErrorCannotBeCreatedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
