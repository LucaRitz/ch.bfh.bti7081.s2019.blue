package ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientPlannerView extends IsView {

    void setPresenter(Presenter presenter);

    void setPatients(List<PatientDto> patients);

    void setMissions(List<MissionDto> missions);

    void reload();

    PatientDto getPatient();

    MissionSeriesDto getSelectedMissionSeries();

    MissionDto getSelectedMissionToAssign();

    interface Presenter {
        void onCreateClicked();
        void onEditClicked();
        void onAssignClicked();
        void onSelectionChange(PatientDto patient, LocalDateTime startDate, LocalDateTime endDate);
    }
}
