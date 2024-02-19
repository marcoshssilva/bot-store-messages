package br.com.marcoshssilva.botstoremessages.domain.services.exceptions;

public class ContactMessageCannotBeCreatedException extends Exception {
    public ContactMessageCannotBeCreatedException(String message, Throwable t) {
        super(message, t);
    }
}
