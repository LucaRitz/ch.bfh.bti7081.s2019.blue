package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.service.EmployeeService;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@UIScope
public class EmployeeDailyOverviewActivity extends BaseActivity implements EmployeeDailyOverviewView.Presenter {

    private final EmployeeDailyOverviewView view;
    private final EmployeeService employeeService;
    private final MissionService missionService;

    @Autowired
    public EmployeeDailyOverviewActivity (EmployeeDailyOverviewView view, EmployeeService employeeService,
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

    }

    @Override
    public void onStartMissionClicked() {

    }
}
