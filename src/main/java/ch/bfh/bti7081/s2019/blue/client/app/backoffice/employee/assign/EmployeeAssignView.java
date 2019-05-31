package ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee.assign;


import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;

import java.util.List;

public interface EmployeeAssignView  extends IsView {

    void setPresenter(EmployeeAssignView.Presenter presenter);

    void setEmployee(EmployeeDto employee);

    void setRecommendedMissions(List<MissionDto> missions);

    interface Presenter {
        void onSaveClicked(MissionDto dto);

        void onCancelClicked();
    }

}