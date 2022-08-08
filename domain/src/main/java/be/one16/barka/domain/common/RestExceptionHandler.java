package be.one16.barka.domain.common;

import be.one16.barka.domain.exceptions.ApiErrorResponse;
import be.one16.barka.domain.exceptions.LinkedEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LinkedEntityNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleLinkedEntityNotFoundException(LinkedEntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
