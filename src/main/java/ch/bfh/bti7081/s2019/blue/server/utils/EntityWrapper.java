package ch.bfh.bti7081.s2019.blue.server.utils;


public class EntityWrapper<T> {
    private final T original;
    private final T modified;

    public EntityWrapper(T original, T modified) {
        this.original = original;
        this.modified = modified;
    }

    public EntityWrapper() {
        this(null, null);
    }

    public T getOriginal() {
        return original;
    }

    public T getModified() {
        return modified;
    }

    public boolean isEmpty(){
        return original == null && modified ==null;
    }
}
