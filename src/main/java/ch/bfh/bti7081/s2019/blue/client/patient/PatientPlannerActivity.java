package ch.bfh.bti7081.s2019.blue.client.patient;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionService;
import ch.bfh.bti7081.s2019.blue.shared.service.PatientService;
import com.google.common.annotations.VisibleForTesting;
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

    @Inject
    public PatientPlannerActivity(PatientPlannerView view,
                                  PatientService patientService,
                                  MissionService missionService) {
        this.view = view;
        this.view.setPresenter(this);
        this.patientService = patientService;
        this.missionService = missionService;
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
    public void onSelectionChange(PatientRefDto patient, Date startDate, Date endDate) {
        List<MissionDto> list = missionService.findMissions(patient.getNumber(), startDate, endDate);
        view.setMissions(list);
    }
}
