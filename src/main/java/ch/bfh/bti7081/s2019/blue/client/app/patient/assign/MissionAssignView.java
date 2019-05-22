package ch.bfh.bti7081.s2019.blue.client.app.patient.assign;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;

import java.util.List;

public interface MissionAssignView  extends IsView {

    void setPresenter(MissionAssignView.Presenter presenter);

    void edit(MissionDto missionDto);

    void setEmployees(List<EmployeeDto> employees);

    interface Presenter {
        void onSaveClicked(MissionDto dto);

        void onCancelClicked();
    }

}



