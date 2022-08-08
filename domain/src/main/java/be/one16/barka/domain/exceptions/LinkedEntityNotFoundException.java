package be.one16.barka.domain.exceptions;

public class LinkedEntityNotFoundException extends RuntimeException {

    public LinkedEntityNotFoundException() {
        super();
    }

    public LinkedEntityNotFoundException(String message) {
        super(message);
    }

    public LinkedEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkedEntityNotFoundException(Throwable cause) {
        super(cause);
    }

}
