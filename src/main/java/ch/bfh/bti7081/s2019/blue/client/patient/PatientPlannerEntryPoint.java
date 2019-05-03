package ch.bfh.bti7081.s2019.blue.client.patient;

import ch.bfh.bti7081.s2019.blue.client.base.Layout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Route(value = "patientplanner", layout = Layout.class)
@UIScope
public class PatientPlannerEntryPoint extends VerticalLayout {

    @Inject
    private PatientPlannerActivity presenter;

    @PostConstruct
    void setUp() {
        add(presenter.getView().asComponent());
    }
}
