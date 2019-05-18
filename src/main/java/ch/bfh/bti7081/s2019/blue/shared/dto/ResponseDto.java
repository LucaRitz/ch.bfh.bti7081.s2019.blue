package ch.bfh.bti7081.s2019.blue.shared.dto;

import java.util.Collections;
import java.util.List;

public class ResponseDto<D> {
    private final D dto;
    private final List<String> errors;

    public ResponseDto() {
        this(null, Collections.emptyList());
    }

    public ResponseDto(String error) {
        this(Collections.singletonList(error));
    }

    public ResponseDto(List<String> errors) {
        this(null, errors);
    }

    public ResponseDto(D dto, List<String> errors) {
        this.dto = dto;
        this.errors = errors;
    }

    public D getDto() {
        return dto;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
