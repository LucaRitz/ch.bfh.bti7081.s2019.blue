package ch.bfh.bti7081.s2019.blue.client.patient.create;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.RepetitionType;
import ch.bfh.bti7081.s2019.blue.shared.dto.ResponseDto;
import ch.bfh.bti7081.s2019.blue.shared.service.PatientService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.function.Consumer;

@Component
@UIScope
public class MissionCreateDialog extends BaseActivity implements MissionCreateView.Presenter {

    private final MissionCreateView view;
    private final PatientService patientService;
    private Dialog dialog;
    private Consumer<MissionSeriesDto> createdMissionSeriesConsumer = null;

    @Inject
    public MissionCreateDialog(MissionCreateView view, PatientService patientService) {
        this.view = view;
        this.view.setPresenter(this);
        this.patientService = patientService;
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

    public void open(Consumer<MissionSeriesDto> createdMissionSeriesConsumer) {
        this.createdMissionSeriesConsumer = createdMissionSeriesConsumer;
        MissionSeriesDto dto = new MissionSeriesDto();
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now());
        dto.setStartTime(LocalTime.now().withMinute(0));
        dto.setEndTime(LocalTime.now().withMinute(0).plusHours(2));
        dto.setRepetitionType(RepetitionType.ONCE);
        view.edit(dto);
        dialog = new Dialog(getView().asComponent());
        dialog.open();
    }

    @Override
    public void onSaveClicked(MissionSeriesDto dto) {

        ResponseDto<Void> response = null; // TODO
        if (response.hasErrors()) {
            // TODO
        } else {
            if(createdMissionSeriesConsumer != null) {
                createdMissionSeriesConsumer.accept(dto);
            }
            dialog.close();
        }
    }

    @Override
    public void onCancelClicked() {
        if(dialog != null) {
            dialog.close();
        }
    }
}
