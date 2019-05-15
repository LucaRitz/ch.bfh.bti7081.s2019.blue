package ch.bfh.bti7081.s2019.blue.client.patient;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.patient.create.MissionCreateDialog;
import ch.bfh.bti7081.s2019.blue.client.patient.edit.MissionEditDialog;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionService;
import ch.bfh.bti7081.s2019.blue.shared.service.PatientService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Component
@UIScope
public class PatientPlannerActivity extends BaseActivity implements PatientPlannerView.Presenter {

    private final PatientPlannerView view;
    private final PatientService patientService;
    private final MissionService missionService;
    private final MissionCreateDialog createDialog;
    private final MissionEditDialog editDialog;

    @Inject
    public PatientPlannerActivity(PatientPlannerView view, PatientService patientService, MissionService missionService,
                                  MissionCreateDialog createDialog, MissionEditDialog editDialog) {
        this.view = view;
        this.view.setPresenter(this);
        this.patientService = patientService;
        this.missionService = missionService;
        this.createDialog = createDialog;
        this.editDialog = editDialog;
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
        List<PatientRefDto> list = patientService.findAll();
        view.setPatients(list);
    }

    @Override
    public void onCreateClicked() {
        createDialog.open(createdMissionSeries -> {
            view.reload();
        }, view.getPatient());
    }

    @Override
    public void onEditClicked() {
        MissionSeriesDto dto = view.getSelectedMissionSeries();

        if(dto == null) {
            System.out.println("fähler");
            return; //TODO
        }

        editDialog.open(dto, editedMissionSeries -> {
            view.reload();
        });
    }

    @Override
    public void onSelectionChange(PatientRefDto patient, Date startDate, Date endDate) {
        List<MissionDto> list = missionService.findMissions(patient.getNumber(), startDate, endDate);
        view.setMissions(list);
    }
}
