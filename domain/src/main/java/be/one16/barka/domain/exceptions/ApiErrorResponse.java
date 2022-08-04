package be.one16.barka.domain.exceptions;

import java.time.LocalDateTime;

public record ApiErrorResponse (LocalDateTime timestamp, int status, String error) {
}
