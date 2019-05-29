package ch.bfh.bti7081.s2019.blue.server;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionMapper {

    private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionMapper.class.getName());

    private final ServerConstants constants;

    @Autowired
    public RestResponseEntityExceptionMapper(ServerConstants constants) {
        this.constants = constants;
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(
            RuntimeException ex, WebRequest request) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.badRequest();

        ValidationException exception = (ValidationException) ex;
        exception.getErrors().forEach(error -> builder.header("errors", error));
        return builder.build();
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException(
            RuntimeException ex, WebRequest request) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(500);


        builder.header("errors", constants.internalServerError());
        LOG.error("Exception of type " + ex.getClass().getName() + " occured !", ex);

        return builder.build();
    }
}
