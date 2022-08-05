package be.one16.barka.klant.domain.exceptions;

import java.time.LocalDateTime;

public record ApiErrorResponse (LocalDateTime timestamp, int status, String error) {
}
