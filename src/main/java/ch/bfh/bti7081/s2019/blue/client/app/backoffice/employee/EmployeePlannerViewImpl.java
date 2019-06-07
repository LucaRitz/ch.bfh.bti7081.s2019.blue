package ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@HtmlImport("src/backoffice/employee/EmployeePlannerViewImpl.html")
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
    private DateRange selectedDateRange = null;

    public EmployeePlannerViewImpl() {
        this.employees.setItemLabelGenerator((ItemLabelGenerator<EmployeeDto>)
                EmployeeDto::getDisplayName);
        setText(getModel().getText()::setTitle, AppConstants.MENU_EMPLOYEEPLANNER);
        setText(getModel().getText()::setColorRecommendationLegend, AppConstants.RECOMMONDATION_AVAILABLE_LEGEND);
        setText(getModel().getText()::setColorBlueLegend, AppConstants.ASSIGNED_TO_PATIENT_LEGEND);

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

            if (entry != null && entry.getId().startsWith("-")) {

                this.selectedDateRange = new DateRange(entry.getStart(), entry.getEnd());
            } else {
                this.selectedDateRange = null;
            }
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

    @Override
    public void setRecommendationEntries(List<DateRange> dateRanges) {
        calendar.addEntries(dateRanges.stream()
                .map(this::toEntry)
                .collect(Collectors.toList()));
    }

    @Override
    public void reload() {
        EmployeeDto selectedEmployee = employees.getValue();

        if (selectedEmployee != null) {
            presenter.onSelectionChange(selectedEmployee, startDate, endDate);
        }
    }

    @Override
    public DateRange getSelectedDateRange() {
        DateRange returnDateRange = this.selectedDateRange;
        this.selectedDateRange = null;
        return returnDateRange;

    }

    @Override
    public EmployeeDto getSelectedEmployee() {
        return employees.getValue();
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

    private Entry toEntry(DateRange dateRange) {

        String missionId = "-" + UUID.randomUUID().toString();

        String title = getTranslation(AppConstants.RECOMMONDATIONS_AVAILABLE.getKey());

        LocalDateTime startDate = dateRange.getStartDate();
        LocalDateTime endDate = dateRange.getEndDate();

        String color = "#0CFF4D";

        return new Entry(missionId, title, startDate, endDate, false, false, color, null);
    }

    @EventHandler
    private void openRecommendationButtonPressed() {
        presenter.onOpenRecommendationClick();
    }


}
