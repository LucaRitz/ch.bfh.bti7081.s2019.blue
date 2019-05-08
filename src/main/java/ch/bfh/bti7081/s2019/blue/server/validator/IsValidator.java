package ch.bfh.bti7081.s2019.blue.server.validator;

import javax.annotation.Nonnull;
import java.util.List;

public interface IsValidator<E> {

    List<String> validate(@Nonnull E entity);
}
