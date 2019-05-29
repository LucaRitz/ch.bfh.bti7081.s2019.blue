package ch.bfh.bti7081.s2019.blue.client.app.employee;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@HtmlImport("src/EmployeePlannerViewImpl.html")
@Tag("employee-planner-view")
@Component
@UIScope
public class EmployeePlannerViewImpl extends BaseViewImpl<EmployeeViewModel> implements EmployeePlannerView {

    @Id
    private ComboBox<EmployeeDto> employees;
    @Id
    private Button previousButton;
    @Id
    private Button nextButton;
    @Id
    private FullCalendar calendar;

    private Presenter presenter;
    private LocalDateTime startDate = null;
    private LocalDateTime endDate = null;
    private Integer selectedMissionId = null;

    public EmployeePlannerViewImpl() {
        this.employees.setItemLabelGenerator((ItemLabelGenerator<EmployeeDto>)
                EmployeeDto::getDisplayName);
        setText(getModel().getText()::setTitle, AppConstants.MENU_EMPLOYEEPLANNER);

        calendar.changeView(CalendarViewImpl.AGENDA_WEEK);
        calendar.setOption("allDaySlot", false);
        calendar.setOption("eventStartEditable", false);
        calendar.setOption("eventDurationEditable", false);
        calendar.setFirstDay(DayOfWeek.MONDAY);
        calendar.setHeightAuto();
        calendar.setLocale(Locale.GERMAN);
        calendar.setNowIndicatorShown(true);

        addEventListeners();
    }

    private void addEventListeners() {
        calendar.addEntryClickedListener((ComponentEventListener<EntryClickedEvent>) event -> {
            Entry entry = event.getEntry();
            this.selectedMissionId = entry != null ? Integer.parseInt(entry.getId()) : null;
        });

        calendar.addViewRenderedListener((ComponentEventListener<ViewRenderedEvent>) event -> onDateRangeChange(event.getIntervalStart(), event.getIntervalEnd()));

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            calendar.previous();
            reloadEntries();
        });
        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            calendar.next();
            reloadEntries();
        });

        employees.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<EmployeeDto>, EmployeeDto>>) event -> reloadEntries());
    }

    private void onDateRangeChange(LocalDate intervalStart, LocalDate intervalEnd) {
        this.startDate = intervalStart.atStartOfDay();
        this.endDate = intervalEnd.plusDays(1).atStartOfDay();

        reloadEntries();
    }

    private void reloadEntries() {
        EmployeeDto selectedEmployee = employees.getValue();

        if (selectedEmployee != null) {
            presenter.onSelectionChange(selectedEmployee, startDate, endDate);
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setEmployees(List<EmployeeDto> employees) {
        this.employees.setItems(employees);

        if (this.employees.getValue() == null && !employees.isEmpty()) {
            this.employees.setValue(employees.get(0));
        }
    }

    @Override
    public void setMissions(List<MissionDto> missions) {

        calendar.removeAllEntries();

        calendar.addEntries(missions.stream()
                .map(this::toEntry)
                .collect(Collectors.toList()));
    }

    private Entry toEntry(MissionDto missionDto) {

        PatientRefDto patient = missionDto.getMissionSeries().getPatient();

        String missionId = String.valueOf(missionDto.getId());

        String title = patient.getDisplayName();

        LocalDateTime startDate = missionDto.getStartDate();

        LocalDateTime endDate = missionDto.getEndDate();

        String color = "#3333ff";

        return new Entry(missionId, title, startDate, endDate, false, false, color, null);
    }
}
