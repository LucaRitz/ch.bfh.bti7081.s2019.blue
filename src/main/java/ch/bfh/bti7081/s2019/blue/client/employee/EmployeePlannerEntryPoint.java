package ch.bfh.bti7081.s2019.blue.client.employee;

import ch.bfh.bti7081.s2019.blue.client.base.Layout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Route(value = "employeeplanner", layout = Layout.class)
@UIScope
public class EmployeePlannerEntryPoint extends Div {

    @Inject
    private EmployeePlannerActivity presenter;

    @PostConstruct
    void setUp() {
        add(presenter.getView().asComponent());
        presenter.start();
    }
}
