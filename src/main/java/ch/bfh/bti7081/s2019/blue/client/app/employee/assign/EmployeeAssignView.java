package ch.bfh.bti7081.s2019.blue.client.app.employee.assign;


import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;

public interface EmployeeAssignView  extends IsView {

    void setPresenter(EmployeeAssignView.Presenter presenter);

    void assign(MissionDto missionDto);

    interface Presenter {
        void onSaveClicked(MissionDto dto);

        void onCancelClicked();
    }

}