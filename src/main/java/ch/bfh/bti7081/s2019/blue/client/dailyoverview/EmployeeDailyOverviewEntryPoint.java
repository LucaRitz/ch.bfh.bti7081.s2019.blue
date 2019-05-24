package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.base.Layout;
import ch.bfh.bti7081.s2019.blue.client.base.Navigation;
import ch.bfh.bti7081.s2019.blue.client.employee.EmployeePlannerActivity;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = Navigation.EMPLOYEE_DAILY_OVERVIEW, layout = Layout.class)
@UIScope
public class EmployeeDailyOverviewEntryPoint extends Div {

    @Autowired
    private EmployeeDailyOverviewActivity presenter;

    @PostConstruct
    void setUp() {
        add(presenter.getView().asComponent());
        presenter.start();
    }
}
