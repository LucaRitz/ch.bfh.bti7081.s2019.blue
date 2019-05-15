package ch.bfh.bti7081.s2019.blue.client.patient;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@HtmlImport("src/PatientPlannerViewImpl.html")
@Tag("patient-planner-view")
@Component
@UIScope
public class PatientPlannerViewImpl extends BaseViewImpl<PatientPlannerViewModel> implements PatientPlannerView {

    @Id
    private ComboBox<PatientRefDto> patients;
    @Id
    private Button previousButton;
    @Id
    private Button nextButton;
    @Id
    private FullCalendar calendar;

    private Presenter presenter;
    private Date startDate = null;
    private Date endDate = null;


    public PatientPlannerViewImpl() {
        this.patients.setItemLabelGenerator((ItemLabelGenerator<PatientRefDto>) person -> person.getDisplayName() + ", " + person.getAge());

        setText(getModel().getText()::setTitle, AppConstants.MENU_PATIENTPLANNER);

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

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.previous());
        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.next());

        patients.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<PatientRefDto>, PatientRefDto>>) event -> reload());
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPatients(List<PatientRefDto> patients) {
        this.patients.setItems(patients);

        if (this.patients.getValue() == null && !patients.isEmpty()) {
            this.patients.setValue(patients.get(0));
        }
    }

    @Override
    public void setMissions(List<MissionDto> missions) {

        calendar.removeAllEntries();
        calendar.addEntries(missions.stream()
                .map(this::toEntry)
                .collect(Collectors.toList()));
    }

    @EventHandler
    private void createButtonPressed() {
        presenter.onCreateClicked();
    }

    @EventHandler
    private void changeEndDateButtonPressed() {
        presenter.onEditClicked();
    }

    private Entry toEntry(MissionDto missionDto) {
        EmployeeDto healthVisitor = missionDto.getHealthVisitor();

        Entry entry = new Entry();

        entry.setTitle(healthVisitor != null ? healthVisitor.getDisplayName() : null);

        entry.setStart(missionDto.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        entry.setEnd(missionDto.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        entry.setColor(healthVisitor != null ? "3333ff" : "#ff3333");

        return entry;
    }

    private void onDateRangeChange(LocalDate intervalStart, LocalDate intervalEnd) {

        this.startDate = Date.from(intervalStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.endDate = Date.from(intervalEnd.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        reload();
    }

    @Override
    public void reload() {

        PatientRefDto selectedPatient = patients.getValue();

        if (selectedPatient != null) {
            presenter.onSelectionChange(selectedPatient, startDate, endDate);
        }
    }
}
