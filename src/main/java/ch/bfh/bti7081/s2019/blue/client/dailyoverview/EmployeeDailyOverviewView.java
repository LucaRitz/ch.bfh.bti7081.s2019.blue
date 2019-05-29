package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;

import java.util.Date;
import java.util.List;

public interface EmployeeDailyOverviewView extends IsView {

    void setPresenter(Presenter presenter);

    void setEmployees(List<EmployeeDto> list);

    void setMissions(List<MissionDto> list);

    void loadMissionEntries();

    interface Presenter {
        void onDetailsClicked();
        void onSelectionChange(EmployeeDto employee, Date startDate, Date endDate);
    }
}
