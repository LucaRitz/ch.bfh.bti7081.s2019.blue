package ch.bfh.bti7081.s2019.blue.client.app.patient.assign;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import com.vaadin.flow.component.ItemLabelGenerator;
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

import java.util.List;

@HtmlImport("src/MissionAssignDialogViewImpl.html")
@Tag("mission-assign-dialog")
@Component
@UIScope
public class MissionAssignViewImpl extends BaseViewImpl<MissionAssignViewModel> implements MissionAssignView {


    private final BeanValidationBinder<MissionDto> binder;

    private Presenter presenter;

    @Id
    private ComboBox<EmployeeDto> comboBox;

    @Id
    private Button saveButton;

    @Id
    private Button cancelButton;

    public MissionAssignViewImpl(){
        binder = new BeanValidationBinder<>(MissionDto.class);
        this.binder.forField(comboBox).bind("healthVisitor");

        this.comboBox.setItemLabelGenerator((ItemLabelGenerator<EmployeeDto>) EmployeeDto::getDisplayName);

        setText(getModel().getText()::setEmployee, AppConstants.MISSION_ASSIGN_EMPLOYEE );
        setText(getModel().getText()::setSave, AppConstants.ACTION_SAVE);
        setText(getModel().getText():: setCancel, AppConstants.ACTION_CANCEL);
    }



    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void edit(MissionDto missionDto) {
        binder.setBean(missionDto);

    }

    @Override
    public void setEmployees(List<EmployeeDto> employees) {

        comboBox.setItems(employees);
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
