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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.templatemodel.TemplateModel;
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
public class PatientPlannerViewImpl extends BaseViewImpl<TemplateModel> implements PatientPlannerView {

    @Id
    private Label title;
    @Id
    private ComboBox<PatientRefDto> patients;
    @Id
    private Button previousButton;
    @Id
    private Button nextButton;
    @Id
    private Button createButton;
    @Id
    private FullCalendar calendar;

    private Presenter presenter;
    private Date startDate = null;
    private Date endDate = null;
    private Integer selectedMissionSeriesId = null;

    public PatientPlannerViewImpl() {

        this.patients.setItemLabelGenerator((ItemLabelGenerator<PatientRefDto>) PatientRefDto::getDisplayName);
        title.setText(getTranslation(AppConstants.MENU_PATIENTPLANNER.getKey()));

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
            this.selectedMissionSeriesId = entry != null ? Integer.parseInt(entry.getId()) : null;
        });
        calendar.addViewRenderedListener((ComponentEventListener<ViewRenderedEvent>) event -> onDateRangeChange(event.getIntervalStart(), event.getIntervalEnd()));

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.previous());
        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> calendar.next());

        patients.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<PatientRefDto>, PatientRefDto>>) event -> reloadEntries());
    }

    private void onDateRangeChange(LocalDate intervalStart, LocalDate intervalEnd) {

        this.startDate = Date.from(intervalStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.endDate = Date.from(intervalEnd.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        reloadEntries();
    }

    private void reloadEntries() {

        PatientRefDto selectedPatient = patients.getValue();

        if (selectedPatient != null) {
            presenter.onSelectionChange(selectedPatient, startDate, endDate);
        }
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

    private Entry toEntry(MissionDto missionDto) {
        EmployeeDto healthVisitor = missionDto.getHealthVisitor();

        String seriesId = String.valueOf(missionDto.getMissionSeries().getId());

        String title = healthVisitor != null ? healthVisitor.getDisplayName() : null;

        LocalDateTime startDate = missionDto.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endDate = missionDto.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        String color = healthVisitor != null ? "3333ff" : "#ff3333";

        return new Entry(seriesId, title, startDate, endDate, false, false, color, null);
    }
}
