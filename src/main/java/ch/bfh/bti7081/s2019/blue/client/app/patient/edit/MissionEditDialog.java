package ch.bfh.bti7081.s2019.blue.client.app.patient.edit;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionSeriesService;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@UIScope
public class MissionEditDialog extends BaseActivity implements MissionEditView.Presenter {

    private final MissionEditView view;
    private final MissionSeriesService missionSeriesService;
    private final DialogFactory dialogFactory;

    @VisibleForTesting
    IsDialog dialog;

    private Listener listener;
    private MissionSeriesDto missionSeriesDto;

    @Autowired
    public MissionEditDialog(MissionEditView view, MissionSeriesService missionSeriesService,
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
        view.edit(missionSeriesDto);
        dialog = dialogFactory.show(view);
    }

    @Override
    public void onSaveClicked(MissionSeriesDto dto) {
        Date endDate = mergeDateTime(dto.getEndDate(), dto.getEndTime());

        missionSeriesService.updateEndDate(dto.getId(), endDate)
                .whenComplete((aVoid, exception) -> {
                    if (exception == null) {
                        if (listener != null) {
                            listener.onSaved();
                        }
                        dialog.close();
                    }
                });
    }

    @Override
    public void onCancelClicked() {
        if (dialog != null) {
            dialog.close();
        }
    }

    public void setProperties(MissionSeriesDto missionSeriesDto) {
        this.missionSeriesDto = missionSeriesDto;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @VisibleForTesting
    Date mergeDateTime(LocalDate date, LocalTime time) {
        return Date.from(
                LocalDateTime.of(date, time)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public interface Listener {
        void onSaved();
    }
}
