package ch.bfh.bti7081.s2019.blue.client.app.patient;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.*;
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
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@HtmlImport("src/backoffice/patient/PatientPlannerViewImpl.html")
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
    private LocalDateTime startDate = null;
    private LocalDateTime endDate = null;
    private List<MissionDto> missions;
    private MissionSeriesDto selectedMissionSeries;
    private MissionDto selectedMission;

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
            if(id.getType() == MissionId.Type.MISSION_SERIES)
                this.selectedMission = getNewMissionDtoById(id, entry);
            else if(id.getType() == MissionId.Type.MISSION)
                this.selectedMission = getMissionById(id);


        });
        calendar.addViewRenderedListener((ComponentEventListener<ViewRenderedEvent>) event -> onDateRangeChange(event.getIntervalStart(), event.getIntervalEnd()));

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.previous());
        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.next());

        patients.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<PatientRefDto>, PatientRefDto>>) event -> reload());
    }

    private void onDateRangeChange(LocalDate intervalStart, LocalDate intervalEnd) {

        this.startDate = intervalStart.atStartOfDay();
        this.endDate = intervalEnd.plusDays(1).atStartOfDay();

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
        MissionSeriesDto returnMissionSeriesDto = this.selectedMissionSeries;
        this.selectedMissionSeries = null;
        return returnMissionSeriesDto;
    }

    @Override
    public MissionDto getSelectedMissionToAssign() {
        MissionDto returnMissionDto = this.selectedMission;
        this.selectedMission = null;
        return returnMissionDto;
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

    @EventHandler
    private void assignButtonPressed() {
        presenter.onAssignClicked();
    }

    private Entry toEntry(MissionDto missionDto) {
        EmployeeDto healthVisitor = missionDto.getHealthVisitor();

        MissionId id = missionIdGenerator.generate(missionDto);

        String title = healthVisitor != null ? healthVisitor.getDisplayName() : null;

        LocalDateTime startDate = missionDto.getStartDate();

        LocalDateTime endDate = missionDto.getEndDate();

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

    private MissionDto getMissionById(MissionId id) {

       if (MissionId.Type.MISSION.equals(id.getType())) {

            return this.missions.stream()
                    .filter(mission -> mission.getId().equals(id.getMissionId()))
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }

    //Returns a new MissionDto out of the selected Appointment
    private MissionDto getNewMissionDtoById(MissionId id, Entry entry) {

        MissionDto dto = new MissionDto();
        dto.setMissionSeries(getMissionSeriesById(id));
        dto.setStartDate(entry.getStart());
        dto.setEndDate(entry.getEnd());

        return dto;

    }
}



