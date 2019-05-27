package ch.bfh.bti7081.s2019.blue.client.app.employee.assign;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class EmployeeAssignDialog extends BaseActivity implements EmployeeAssignView.Presenter{

    private final EmployeeAssignView view;
    //private final EmployeeAssignService employeeAssignService;
    private final DialogFactory dialogFactory;


    @VisibleForTesting
    IsDialog dialog;

    private EmployeeAssignDialog.Listener listener;
    private MissionDto missionDto;
    private DateRange dateRange;


    @Autowired
    public EmployeeAssignDialog(EmployeeAssignView view, /*EmployeeAssingService employeeAssingService,*/
                               DialogFactory dialogFactory ){
        this.view = view;
        this.view.setPresenter(this);
        //this.employeeAssignService = employeeAssingService;
        this.dialogFactory = dialogFactory;
    }


    @Override
    public void start() {
        view.assign(missionDto);
        dialog = dialogFactory.show(view);
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void onSaveClicked(MissionDto dto) {
        //Todo
    }

    @Override
    public void onCancelClicked() {
        if (dialog != null)
            dialog.close();
    }

    public void setProperties(DateRange dateRange) {this.dateRange=dateRange;}

    public void setListener(Listener listener) { this.listener = listener; }

    public interface Listener {
        void onSaved();
    }

}
