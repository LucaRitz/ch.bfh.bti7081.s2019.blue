package ch.bfh.bti7081.s2019.blue.client.app.home;

import ch.bfh.bti7081.s2019.blue.client.app.base.Layout;
import ch.bfh.bti7081.s2019.blue.client.app.base.Navigation;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = Navigation.HOME, layout = Layout.class)
@PWA(name = "MHC PMS", shortName = "MHC PMS", enableInstallPrompt = false)
@UIScope
public class HomeEntryPoint extends Div {

    @Autowired
    private HomeActivity presenter;

    @PostConstruct
    void setUp() {
        add(presenter.getView().asComponent());
        presenter.start();
    }
}
