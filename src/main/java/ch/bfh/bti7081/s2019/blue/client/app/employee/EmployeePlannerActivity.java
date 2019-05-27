package ch.bfh.bti7081.s2019.blue.client.app.employee;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.employee.assign.EmployeeAssignDialog;
import ch.bfh.bti7081.s2019.blue.client.app.patient.assign.MissionAssignDialog;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeService;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.Entry;

import java.util.*;

@Component
@UIScope
public class EmployeePlannerActivity extends BaseActivity implements EmployeePlannerView.Presenter {

    private final EmployeePlannerView view;
    private final EmployeeService employeeService;

    private final EmployeeAssignDialog assignDialog;

    @Autowired
    public EmployeePlannerActivity(EmployeePlannerView view, EmployeeService employeeService,
                                   EmployeeAssignDialog assignDialog) {
        this.view = view;
        this.view.setPresenter(this);
        this.employeeService = employeeService;
        this.assignDialog = assignDialog;
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
    public void onOpenRecommendationClick() {
        DateRange dateRange = view.getSelectedDateRange();
        if (dateRange == null) {
            view.showNotification(AppConstants.PATIENT_PLANNER_NO_SELECTED_MISSION.getKey());
            return;
        }

        assignDialog.setProperties(dateRange);
        assignDialog.setListener(view::reload);
        assignDialog.start();
    }

    @Override
    public void onSelectionChange(EmployeeDto employee, Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            employeeService.missions(employee.getId()).find(startDate, endDate)
                    .done(view::setMissions);
            employeeService.missionRecommondationPlaceholders(employee.getId()).find(startDate, endDate)
                    .done((dateRanges) -> {
                        if(dateRanges != null){
                            view.setRecommendationEntries(dateRanges);
                        }
                    });
        }
    }
}
