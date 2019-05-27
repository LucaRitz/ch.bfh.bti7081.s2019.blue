package ch.bfh.bti7081.s2019.blue.client.app.employee;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;

import java.util.Date;
import java.util.List;

public interface EmployeePlannerView extends IsView {

    void setPresenter(Presenter presenter);

    void setEmployees(List<EmployeeDto> employees);

    void setMissions(List<MissionDto> missions);

    void setRecommendationEntries(List<DateRange> dateRanges);

    void reload();

    DateRange getSelectedDateRange();

    interface Presenter {
        void onOpenRecommendationClick();
        void onSelectionChange(EmployeeDto employee, Date startDate, Date endDate);
    }
}
