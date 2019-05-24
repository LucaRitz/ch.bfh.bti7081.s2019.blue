package ch.bfh.bti7081.s2019.blue.server.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<String> errors;

    public ValidationException() {
        this(Collections.emptyList());
    }

    public ValidationException(String error) {
        this(Collections.singletonList(error));
    }

    public ValidationException(List<String> errors) {
        this.errors = new ArrayList<>(errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
