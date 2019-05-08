package ch.bfh.bti7081.s2019.blue.server.validator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ValidationData<E> {

    private final E original;
    private final E modified;

    public ValidationData(@Nonnull E modified) {
        this(null, modified);
    }

    public ValidationData(@Nullable E original, @Nonnull E modified) {
        this.original = original;
        this.modified = modified;
    }

    public E getOriginal() {
        return original;
    }

    public E getModified() {
        return modified;
    }
}
