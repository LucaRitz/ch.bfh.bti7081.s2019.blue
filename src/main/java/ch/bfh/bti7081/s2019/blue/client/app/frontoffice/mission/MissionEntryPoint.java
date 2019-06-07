package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.mission;

import ch.bfh.bti7081.s2019.blue.client.app.base.Layout;
import ch.bfh.bti7081.s2019.blue.client.app.base.Navigation;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = Navigation.MISSION, layout = Layout.class)
@UIScope
public class MissionEntryPoint extends Div implements HasUrlParameter<Integer> {

    @Autowired
    private MissionActivity presenter;

    @PostConstruct
    void setUp() {
        add(presenter.getView().asComponent());
    }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        presenter.setMissionId(parameter);
        presenter.start();
    }
}
