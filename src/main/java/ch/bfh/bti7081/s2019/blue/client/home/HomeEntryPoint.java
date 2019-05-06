package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.client.base.Layout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Route(value = "home", layout = Layout.class)
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
@UIScope
public class HomeEntryPoint extends Div {

    @Inject
    private HomeActivity presenter;

    @PostConstruct
    void setUp() {
        add(presenter.getView().asComponent());
        presenter.start();
    }
}
