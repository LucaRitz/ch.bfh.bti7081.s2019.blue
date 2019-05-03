package ch.bfh.bti7081.s2019.blue.server.persistence.builder;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Home;

public class HomeBuilder {
    private final Home home;

    public HomeBuilder() {
        home = new Home();
    }

    public HomeBuilder setReference(Long referenceId) {
        home.setReference(referenceId);
        return this;
    }

    public Home build() {
        return home;
    }
}
