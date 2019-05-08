package ch.bfh.bti7081.s2019.blue.server.persistence.builder;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Home;

public class HomeBuilder extends Builder<Home> {
    public HomeBuilder() {
        super(new Home());
    }

    public HomeBuilder setReference(Long referenceId) {
        entity.setReference(referenceId);
        return this;
    }
}
