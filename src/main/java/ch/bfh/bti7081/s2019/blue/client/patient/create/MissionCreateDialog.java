package ch.bfh.bti7081.s2019.blue.client.patient.create;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.RepetitionType;
import ch.bfh.bti7081.s2019.blue.shared.dto.ResponseDto;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionSeriesService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@UIScope
public class MissionCreateDialog extends BaseActivity implements MissionCreateView.Presenter {

    private final MissionCreateView view;
    private final MissionSeriesService missionSeriesService;
    private final DialogFactory dialogFactory;

    @VisibleForTesting
    IsDialog dialog;

    private Listener listener;
    private PatientRefDto patient;

    @Autowired
    public MissionCreateDialog(MissionCreateView view, MissionSeriesService missionSeriesService,
                               DialogFactory dialogFactory) {
        this.view = view;
        this.view.setPresenter(this);
        this.missionSeriesService = missionSeriesService;
        this.dialogFactory = dialogFactory;
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        MissionSeriesDto dto = new MissionSeriesDto();
        dto.setPatient(patient);
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now());
        dto.setStartTime(LocalTime.now().withMinute(0).withSecond(0).withNano(0));
        dto.setEndTime(LocalTime.now().withMinute(0).withSecond(0).withNano(0).plusHours(2));
        dto.setRepetitionType(RepetitionType.ONCE);
        view.edit(dto);
        dialog = dialogFactory.show(view);
    }

    @Override
    public void onSaveClicked(MissionSeriesDto dto) {
        ResponseDto<Void> response = missionSeriesService.create(dto);
        if (response.hasErrors()) {
            view.showTranslatedNotification(response.getErrors());
        } else {
            if (listener != null) {
                listener.onSaved();
            }
            dialog.close();
        }
    }

    @Override
    public void onCancelClicked() {
        if (dialog != null) {
            dialog.close();
        }
    }

    public void setProperties(PatientRefDto patient) {
        this.patient = patient;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onSaved();
    }
}
