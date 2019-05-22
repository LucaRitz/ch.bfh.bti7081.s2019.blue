package ch.bfh.bti7081.s2019.blue.client.app.patient;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
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

@HtmlImport("src/PatientPlannerViewImpl.html")
@Tag("patient-planner-view")
@Component
@UIScope
public class PatientPlannerViewImpl extends BaseViewImpl<PatientPlannerViewModel> implements PatientPlannerView {

    private final MissionIdGenerator missionIdGenerator;

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
    private List<MissionDto> missions;
    private MissionSeriesDto selectedMissionSeries;

    @Autowired
    public PatientPlannerViewImpl(MissionIdGenerator missionIdGenerator) {
        this.missionIdGenerator = missionIdGenerator;

        this.patients.setItemLabelGenerator((ItemLabelGenerator<PatientRefDto>) PatientRefDto::getDisplayName);
        setText(getModel().getText()::setTitle, AppConstants.MENU_PATIENTPLANNER);
        setText(getModel().getText()::setColorRedLegend, AppConstants.COLOR_RED_LEGEND);
        setText(getModel().getText()::setColorBlueLegend, AppConstants.COLOR_BLUE_LEGEND);

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
            if (entry == null) {
                return;
            }

            MissionId id = missionIdGenerator.parse(entry.getId());
            Notification notification = new Notification("Click on " + id.getType() + " Mission with id=" + id + " was registered\n" +
                    "Start: " + entry.getStart().toString() + " End: " + entry.getEnd(), 3000);
            notification.open();
            this.selectedMissionSeries = getMissionSeriesById(id);


        });
        calendar.addViewRenderedListener((ComponentEventListener<ViewRenderedEvent>) event -> onDateRangeChange(event.getIntervalStart(), event.getIntervalEnd()));

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.previous());
        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.next());

        patients.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<PatientRefDto>, PatientRefDto>>) event -> reload());
    }

    private void onDateRangeChange(LocalDate intervalStart, LocalDate intervalEnd) {

        this.startDate = Date.from(intervalStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.endDate = Date.from(intervalEnd.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        reload();
    }

    public void reload() {

        PatientRefDto selectedPatient = patients.getValue();

        if (selectedPatient != null) {
            presenter.onSelectionChange(selectedPatient, startDate, endDate);
        }
    }

    @Override
    public PatientRefDto getPatient() {
        return this.patients.getValue();
    }

    @Override
    public MissionSeriesDto getSelectedMissionSeries() {
        return this.selectedMissionSeries;
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
        this.missions = missions;
        this.selectedMissionSeries = null;

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

        MissionId id = missionIdGenerator.generate(missionDto);

        String title = healthVisitor != null ? healthVisitor.getDisplayName() : null;

        LocalDateTime startDate = missionDto.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endDate = missionDto.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        String color = healthVisitor != null ? "#3333ff" : "#ff3333";

        return new Entry(id.toString(), title, startDate, endDate, false, false, color, null);
    }

    private MissionSeriesDto getMissionSeriesById(MissionId id) {

        if (MissionId.Type.MISSION_SERIES.equals(id.getType())) {

            return this.missions.stream()
                    .map(MissionDto::getMissionSeries)
                    .filter(series -> series.getId().equals(id.getSeriesId()))
                    .findFirst()
                    .orElse(null);

        } else if (MissionId.Type.MISSION.equals(id.getType())) {

            return this.missions.stream()
                    .filter(mission -> mission.getId().equals(id.getMissionId()))
                    .map(MissionDto::getMissionSeries)
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }
}
