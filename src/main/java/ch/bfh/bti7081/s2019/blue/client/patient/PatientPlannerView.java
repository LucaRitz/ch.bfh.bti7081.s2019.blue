package ch.bfh.bti7081.s2019.blue.client.patient;

import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;

import java.util.Date;
import java.util.List;

public interface PatientPlannerView extends IsView {

    void setPresenter(Presenter presenter);

    void setPatients(List<PatientRefDto> patients);

    void setMissions(List<MissionDto> missions);

    interface Presenter {

        void onSelectionChange(PatientRefDto patient, Date startDate, Date endDate);
    }
}
