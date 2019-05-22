package ch.bfh.bti7081.s2019.blue.client.app.employee;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeService;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@UIScope
public class EmployeePlannerActivity extends BaseActivity implements EmployeePlannerView.Presenter {

    private final EmployeePlannerView view;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeePlannerActivity(EmployeePlannerView view, EmployeeService employeeService) {
        this.view = view;
        this.view.setPresenter(this);
        this.employeeService = employeeService;
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        loadMasterdata();
    }

    @VisibleForTesting
    void loadMasterdata() {
        employeeService.find(EmployeeRole.HEALTH_VISITOR).whenComplete((employees, exception) -> {
            if (employees != null) {
                view.setEmployees(employees);
            }
        });
    }

    @Override
    public void onSelectionChange(EmployeeDto employee, Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            employeeService.missions(employee.getId()).find(startDate, endDate).whenComplete((missions, exception) -> {
                if (missions != null) {
                    view.setMissions(missions);
                }
            });
        }
    }
}
