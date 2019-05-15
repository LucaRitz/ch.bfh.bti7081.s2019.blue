package ch.bfh.bti7081.s2019.blue.client.patient.edit;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.service.PatientService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.function.Consumer;

@Component
@UIScope
public class MissionEditDialog extends BaseActivity implements MissionEditView.Presenter {

    private final MissionEditView view;
    private final PatientService patientService;
    private Dialog dialog;
    private Consumer<MissionSeriesDto> editedMissionSeriesConsumer = null;

    @Inject
    public MissionEditDialog(MissionEditView view, PatientService patientService) {
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

    public void open(MissionSeriesDto dto, Consumer<MissionSeriesDto> editedMissionSeriesConsumer) {
        this.editedMissionSeriesConsumer = editedMissionSeriesConsumer;
        view.edit(dto);
        dialog = new Dialog(getView().asComponent());
        dialog.open();
    }

    @Override
    public void onSaveClicked(MissionSeriesDto dto) {

        if(editedMissionSeriesConsumer != null) {
            editedMissionSeriesConsumer.accept(dto);
        }
        dialog.close();
    }

    @Override
    public void onCancelClicked() {
        if(dialog != null) {
            dialog.close();
        }
    }
}
