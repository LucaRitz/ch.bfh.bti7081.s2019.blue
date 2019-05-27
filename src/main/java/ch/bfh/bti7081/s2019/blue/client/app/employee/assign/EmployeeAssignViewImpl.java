package ch.bfh.bti7081.s2019.blue.client.app.employee.assign;


import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientDto;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@HtmlImport("src/MissionAssignDialogViewImpl.html")
@Tag("mission-assign-dialog")
@Component
@UIScope
public class EmployeeAssignViewImpl extends BaseViewImpl<EmployeeAssignViewModel> implements EmployeeAssignView {

    private final BeanValidationBinder<MissionDto> binder;

    private EmployeeAssignView.Presenter presenter;

    @Id
    private ComboBox comboBox;

    @Id
    private Button saveButton;

    @Id
    private Button cancelButton;

    public EmployeeAssignViewImpl(){
        binder = new BeanValidationBinder<>(MissionDto.class);
        this.binder.forField(comboBox).bind("healthVisitor");

        setText(getModel().getText()::setEmployee, AppConstants.MISSION_ASSIGN_EMPLOYEE );
        setText(getModel().getText()::setSave, AppConstants.ACTION_SAVE);
        setText(getModel().getText():: setCancel, AppConstants.ACTION_CANCEL);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void assign(MissionDto missionDto) {
        binder.setBean(missionDto);
        comboBox.setItems(new ArrayList<PatientDto>());
    }

    @EventHandler
    private void saveButtonPressed() {
        BinderValidationStatus<MissionDto> status = binder.validate();
        if(!status.hasErrors()){
            presenter.onSaveClicked((binder.getBean()));
        }
    }

    @EventHandler
    private void cancelButtonPressed() { presenter.onCancelClicked();}
}
