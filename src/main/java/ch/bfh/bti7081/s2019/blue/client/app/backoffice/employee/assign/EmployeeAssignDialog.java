package ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee.assign;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeService;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionService;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class EmployeeAssignDialog extends BaseActivity implements EmployeeAssignView.Presenter {

    private final EmployeeAssignView view;
    private final DialogFactory dialogFactory;
    private final EmployeeService employeeService;
    private final MissionService missionService;

    private IsDialog dialog;

    private EmployeeAssignDialog.Listener listener;
    private DateRange dateRange;
    private EmployeeDto employee;

    @Autowired
    public EmployeeAssignDialog(EmployeeAssignView view, EmployeeService employeeService,
                                MissionService missionService, DialogFactory dialogFactory) {
        this.view = view;
        this.view.setPresenter(this);
        this.employeeService = employeeService;
        this.missionService = missionService;
        this.dialogFactory = dialogFactory;
    }

    @Override
    public void start() {
        view.setEmployee(employee);
        Integer employeeId = employee.getId();
        employeeService.missionRecommondations(employeeId)
                .find(dateRange.getStartDate(), dateRange.getEndDate())
                .done(view::setRecommendedMissions);

        dialog = dialogFactory.show(view);


    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void onSaveClicked(MissionDto dto) {
        missionService.create(dto)
                .done(aVoid -> {
                    if (listener != null) {
                        listener.onSaved();
                    }
                    dialog.close();
                });
    }

    @Override
    public void onCancelClicked() {
        if (dialog != null)
            dialog.close();
    }

    public void setProperties(DateRange dateRange, EmployeeDto employee) {
        this.dateRange = dateRange;
        this.employee = employee;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onSaved();
    }

}
