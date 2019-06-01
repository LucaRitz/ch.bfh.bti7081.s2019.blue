package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeService;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionOrderBy;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@UIScope
public class EmployeeDailyOverviewActivity extends BaseActivity implements EmployeeDailyOverviewView.Presenter {

    private final EmployeeDailyOverviewView view;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeDailyOverviewActivity (EmployeeDailyOverviewView view, EmployeeService employeeService) {
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
        employeeService.find(EmployeeRole.HEALTH_VISITOR)
                .done(view::setEmployees);
    }

    @Override
    public void onSelectionChange(EmployeeDto employee, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            employeeService.missions(employee.getId()).find(startDate, endDate, MissionOrderBy.START_DATE, true)
                    .done(view::setMissions);
        }
    }

    @Override
    public void onDetailsClicked() {

    }
}
