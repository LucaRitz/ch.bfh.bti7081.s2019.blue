package ch.bfh.bti7081.s2019.blue.client.patient.create;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.client.widget.EnumRenderer;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.RepetitionType;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@HtmlImport("src/MissionCreateDialogViewImpl.html")
@Tag("mission-create-dialog")
@Component
@UIScope
public class MissionCreateViewImpl extends BaseViewImpl<MissionCreateViewModel> implements MissionCreateView {

    private final BeanValidationBinder<MissionSeriesDto> binder;

    private Presenter presenter;

    @Id
    private DatePicker startDate;

    @Id
    private TimePicker startTime;

    @Id
    private DatePicker endDate;

    @Id
    private TimePicker endTime;

    @Id
    private RadioButtonGroup<RepetitionType> repetitionType;

    @Id
    private Button saveButton;

    @Id
    private Button cancelButton;

    public MissionCreateViewImpl() {
        binder = new BeanValidationBinder<>(MissionSeriesDto.class);
        this.binder.forField(startDate).bind("startDate");
        this.binder.forField(endDate).bind("endDate");
        this.binder.forField(startTime).bind("startTime");
        this.binder.forField(endTime).bind("endTime");
        this.binder.forField(repetitionType).bind("repetitionType");

        List<RepetitionType> repetitionTypes = Arrays.asList(RepetitionType.values());
        repetitionType.setDataProvider(DataProvider.ofCollection(repetitionTypes));
        repetitionType.setRenderer(new EnumRenderer<>(RepetitionType.class, this::getTranslation).asRenderer());

        setText(getModel().getText()::setStartDate, AppConstants.MISSION_CREATE_START_DATE);
        setText(getModel().getText()::setStartTime, AppConstants.MISSION_CREATE_START_TIME);
        setText(getModel().getText()::setEndDate, AppConstants.MISSION_CREATE_END_DATE);
        setText(getModel().getText()::setEndTime, AppConstants.MISSION_CREATE_END_TIME);
        setText(getModel().getText()::setRepetitionType, AppConstants.MISSION_CREATE_REPETITION_TYPE);

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
        startDate.setLocale(getLocale());
        endDate.setLocale(getLocale());
        startTime.setLocale(getLocale());
        endTime.setLocale(getLocale());
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
