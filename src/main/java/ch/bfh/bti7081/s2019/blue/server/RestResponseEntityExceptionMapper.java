package ch.bfh.bti7081.s2019.blue.server;

import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionMapper {

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.badRequest();

        ValidationException exception = (ValidationException) ex;
        exception.getErrors().forEach(error -> builder.header("errors", error));
        return builder.build();
    }
}
