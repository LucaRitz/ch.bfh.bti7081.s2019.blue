package ch.bfh.bti7081.s2019.blue.client.employee;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@HtmlImport("src/EmployeePlannerViewImpl.html")
@Tag("employee-planner-view")
@Component
@UIScope
public class EmployeePlannerViewImpl extends BaseViewImpl<TemplateModel> implements EmployeePlannerView {

    @Id
    private Label title;
    @Id
    private ComboBox<EmployeeDto> employees;
    @Id
    private Button previousButton;
    @Id
    private Button nextButton;
    @Id
    private FullCalendar calendar;

    private Presenter presenter;
    private Date startDate = null;
    private Date endDate = null;

    public EmployeePlannerViewImpl() {

        this.employees.setItemLabelGenerator((ItemLabelGenerator<EmployeeDto>)
                EmployeeDto::getDisplayName);
        title.setText(getTranslation(AppConstants.MENU_EMPLOYEEPLANNER.getKey()));

        calendar.changeView(CalendarViewImpl.AGENDA_WEEK);
        calendar.setOption("allDaySlot", false);
        calendar.setOption("eventStartEditable", false);
        calendar.setOption("eventDurationEditable", false);
        calendar.setFirstDay(DayOfWeek.MONDAY);
        calendar.setHeightAuto();
        calendar.setLocale(Locale.GERMAN);

        addEventListeners();
    }

    private void addEventListeners() {
        calendar.addEntryClickedListener((ComponentEventListener<EntryClickedEvent>) event -> {
            // TODO: do something
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
        this.startDate = Date.from(intervalStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.endDate = Date.from(intervalEnd.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

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

        Entry entry = new Entry();

        entry.setTitle(patient.getDisplayName());

        entry.setStart(missionDto.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        entry.setEnd(missionDto.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        entry.setColor("dddd00");

        return entry;
    }
}
