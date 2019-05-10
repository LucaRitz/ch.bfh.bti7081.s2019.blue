package ch.bfh.bti7081.s2019.blue.client.employee;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.service.EmployeeService;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Component
@UIScope
public class EmployeePlannerActivity extends BaseActivity implements EmployeePlannerView.Presenter {

    private final EmployeePlannerView view;
    private final EmployeeService employeeService;
    private final MissionService missionService;

    @Inject
    public EmployeePlannerActivity(EmployeePlannerView view,
                                   EmployeeService employeeService,
                                   MissionService missionService) {
        this.view = view;
        this.view.setPresenter(this);
        this.employeeService = employeeService;
        this.missionService = missionService;
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
        List<EmployeeDto> list = employeeService.findAllHealthVisitors();
        view.setEmployees(list);
    }

    @Override
    public void onSelectionChange(EmployeeDto employee, Date startDate, Date endDate) {
        List<MissionDto> list = missionService.findMissionsForEmployee(employee.getId(), startDate, endDate);
        view.setMissions(list);
    }
}
