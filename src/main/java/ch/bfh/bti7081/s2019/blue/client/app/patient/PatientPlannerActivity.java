package ch.bfh.bti7081.s2019.blue.client.app.patient;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.patient.assign.MissionAssignDialog;
import ch.bfh.bti7081.s2019.blue.client.app.patient.create.MissionCreateDialog;
import ch.bfh.bti7081.s2019.blue.client.app.patient.edit.MissionEditDialog;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionService;
import ch.bfh.bti7081.s2019.blue.client.ws.PatientService;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@UIScope
public class PatientPlannerActivity extends BaseActivity implements PatientPlannerView.Presenter {

    private final PatientPlannerView view;
    private final PatientService patientService;
    private final MissionService missionService;
    private final MissionCreateDialog createDialog;
    private final MissionEditDialog editDialog;
    private final MissionAssignDialog assignDialog;

    @Autowired
    public PatientPlannerActivity(PatientPlannerView view, PatientService patientService, MissionService missionService,
                                  MissionCreateDialog createDialog, MissionEditDialog editDialog, MissionAssignDialog assignDialog) {
        this.view = view;
        this.view.setPresenter(this);
        this.patientService = patientService;
        this.missionService = missionService;
        this.createDialog = createDialog;
        this.editDialog = editDialog;
        this.assignDialog = assignDialog;
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        loadMasterdata();
    }

    @Override
    public void onCreateClicked() {
        createDialog.setProperties(view.getPatient());
        createDialog.setListener(view::reload);
        createDialog.start();
    }

    @Override
    public void onEditClicked() {
        MissionSeriesDto dto = view.getSelectedMissionSeries();

        if (dto == null) {
            view.showNotification(AppConstants.PATIENT_PLANNER_NO_SELECTED_MISSION.getKey());
            return;
        }

        editDialog.setProperties(dto);
        editDialog.setListener(view::reload);
        editDialog.start();
    }

    @Override
    public void onAssignClicked() {
        MissionDto dto = view.getSelectedMissionToAssign();
        if (dto == null) {
            view.showNotification(AppConstants.PATIENT_PLANNER_NO_SELECTED_MISSION.getKey());
            return;
        }

        assignDialog.setProperties(dto);
        assignDialog.setListener(view::reload);
        assignDialog.start();
    }

    @Override
    public void onSelectionChange(PatientRefDto patient, Date startDate, Date endDate) {
        missionService.find(patient.getNumber(), startDate, endDate)
                .done(view::setMissions);
    }

    @VisibleForTesting
    void loadMasterdata() {
        patientService.get()
                .done(view::setPatients);
    }
}
