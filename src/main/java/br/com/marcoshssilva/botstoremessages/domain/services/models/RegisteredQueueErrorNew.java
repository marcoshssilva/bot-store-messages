package br.com.marcoshssilva.botstoremessages.domain.services.models;

import java.util.Arrays;
import java.util.Objects;

public record RegisteredQueueErrorNew(String errorMessage, byte[] errorData) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredQueueErrorNew that = (RegisteredQueueErrorNew) o;
        return Objects.equals(errorMessage, that.errorMessage) && Arrays.equals(errorData, that.errorData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(errorMessage);
        result = 31 * result + Arrays.hashCode(errorData);
        return result;
    }

    @Override
    public String toString() {
        return "RegisteredQueueErrorNew(" + "errorMessage=" + errorMessage + ", errorData=" + Arrays.toString(errorData) + ')';
    }
}
