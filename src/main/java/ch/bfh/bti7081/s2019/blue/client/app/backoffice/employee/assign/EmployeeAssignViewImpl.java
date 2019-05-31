package ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee.assign;


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
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.List;

@HtmlImport("src/backoffice/employee/EmployeeAssignDialogViewImpl.html")
@Tag("employee-assign-dialog")
@Component
@UIScope
public class EmployeeAssignViewImpl extends BaseViewImpl<EmployeeAssignViewModel> implements EmployeeAssignView {

    private EmployeeAssignView.Presenter presenter;

    private EmployeeDto employee;

    @Id
    private ComboBox<MissionDto> comboBox;

    @Id
    private Button saveButton;

    @Id
    private Button cancelButton;

    public EmployeeAssignViewImpl(){

        this.comboBox.setItemLabelGenerator((ItemLabelGenerator<MissionDto>) MissionDto::getDisplayName );


        setText(getModel().getText()::setMissions, AppConstants.ASSIGN_TO_MISSION );
        setText(getModel().getText()::setSave, AppConstants.ACTION_SAVE);
        setText(getModel().getText():: setCancel, AppConstants.ACTION_CANCEL);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;

    }

    @Override
    public void setRecommendedMissions(List<MissionDto> missions) {
        comboBox.setItems(missions);

        if (this.comboBox.getValue() == null && !missions.isEmpty()) {
            this.comboBox.setValue(missions.get(0));
        }
    }

    @EventHandler
    private void saveButtonPressed() {
        MissionDto missionDto = comboBox.getValue();
        if(missionDto != null) {
            missionDto.setHealthVisitor(employee);
            presenter.onSaveClicked(missionDto);
        }
    }

    @EventHandler
    private void cancelButtonPressed() { presenter.onCancelClicked();}
}
