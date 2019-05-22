package ch.bfh.bti7081.s2019.blue.server.validator;

import javax.annotation.Nonnull;
import java.util.List;

public interface IsValidator<E> {

    void validate(@Nonnull E entity);

    default void checkErrorsAndThrow(List<String> errors) {
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
