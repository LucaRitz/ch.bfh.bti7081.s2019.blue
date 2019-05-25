package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeMissionSubService;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeService;
import ch.bfh.bti7081.s2019.blue.client.ws.PatientService;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
    public void onSelectionChange(EmployeeDto employee, Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            employeeService.missions(employee.getId()).find(startDate, endDate)
                    .done(view::setMissions);
        }
    }

    @Override
    public void onDetailsClicked() {

    }
}
