package ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeePlannerView extends IsView {

    void setPresenter(Presenter presenter);

    void setEmployees(List<EmployeeDto> employees);

    void setMissions(List<MissionDto> missions);

    void setRecommendationEntries(List<DateRange> dateRanges);

    void reload();

    DateRange getSelectedDateRange();
    EmployeeDto getSelectedEmployee();

    interface Presenter {
        void onOpenRecommendationClick();
        void onSelectionChange(EmployeeDto employee, LocalDateTime startDate, LocalDateTime endDate);
    }
}
