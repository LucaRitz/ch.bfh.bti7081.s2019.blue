package ch.bfh.bti7081.s2019.blue.client.employee;

import ch.bfh.bti7081.s2019.blue.client.base.Layout;
import ch.bfh.bti7081.s2019.blue.client.base.Navigation;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = Navigation.EMPLOYEE_PLANNER, layout = Layout.class)
@UIScope
public class EmployeePlannerEntryPoint extends Div {

    @Autowired
    private EmployeePlannerActivity presenter;

    @PostConstruct
    void setUp() {
        add(presenter.getView().asComponent());
        presenter.start();
    }
}
