package be.one16.barka.klant.common.exceptions;

public class KlantNotFoundException extends IllegalArgumentException {
    public KlantNotFoundException(String message) {
        super(message);
    }

}
