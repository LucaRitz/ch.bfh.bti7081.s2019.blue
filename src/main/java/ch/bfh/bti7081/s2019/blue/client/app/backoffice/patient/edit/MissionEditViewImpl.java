package ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient.edit;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@HtmlImport("src/backoffice/patient/MissionEditDialogViewImpl.html")
@Tag("mission-edit-dialog")
@Component
@UIScope
public class MissionEditViewImpl extends BaseViewImpl<MissionEditViewModel> implements MissionEditView {

    private final BeanValidationBinder<MissionSeriesDto> binder;

    private Presenter presenter;

    @Id
    private DatePicker endDate;

    @Id
    private Button saveButton;

    @Id
    private Button cancelButton;

    public MissionEditViewImpl() {
        binder = new BeanValidationBinder<>(MissionSeriesDto.class);
        this.binder.forField(endDate).bind("endDate");

        setText(getModel().getText()::setEndDate, AppConstants.MISSION_EDIT_END_DATE);
        setText(getModel().getText()::setSave, AppConstants.ACTION_SAVE);
        setText(getModel().getText()::setCancel, AppConstants.ACTION_CANCEL);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void edit(MissionSeriesDto missionSeriesDto) {
        binder.setBean(missionSeriesDto);
        endDate.setLocale(getLocale());
    }

    @EventHandler
    private void saveButtonPressed() {
        BinderValidationStatus<MissionSeriesDto> status = binder.validate();
        if (!status.hasErrors()) {
            presenter.onSaveClicked(binder.getBean());
        }
    }

    @EventHandler
    private void cancelButtonPressed() {
        presenter.onCancelClicked();
    }
}
