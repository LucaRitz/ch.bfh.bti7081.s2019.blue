package ch.bfh.bti7081.s2019.blue.server.persistence.builder;

import org.springframework.data.domain.Example;

public class Builder<E> {

    protected final E entity;

    protected Builder(E entity) {
        this.entity = entity;
    }

    public Example<E> build() {
        return Example.of(entity);
    }
}
