package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.mission;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.*;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@HtmlImport("src/frontoffice/mission/MissionViewImpl.html")
@Tag("mission-detail-view")
@Component
@UIScope
public class MissionViewImpl extends BaseViewImpl<MissionModel> implements MissionView {

    @Id
    private Button startButton;
    @Id
    private Button finishButton;
    @Id
    private Button showReportButton;

    @Id
    private Label nameLabel;
    @Id
    private Label startDateLabel;
    @Id
    private Label endDateLabel;
    @Id
    private Label locationLabel;
    @Id
    private Div medicationDiv;
    @Id
    private Div diagnosesDiv;
    @Id
    private Label doctorLabel;

    @Id
    private IFrame mapsLocationFrame;

    private Presenter presenter;

    @Autowired
    public MissionViewImpl() {

        setText(getModel().getText()::setTitle, AppConstants.MISSION);
        setText(getModel().getText()::setSubtitle, AppConstants.MISSION_DETAILS);

        setText(getModel().getText()::setStartMission, AppConstants.MISSION_START);
        setText(getModel().getText()::setFinishMission, AppConstants.MISSION_END);
        setText(getModel().getText()::setShowReport, AppConstants.MISSION_SHOW_REPORT);

        setText(getModel().getText()::setPatientName, AppConstants.PATIENT_NAME);
        setText(getModel().getText()::setPatientLocation, AppConstants.PATIENT_LOCATION);
        setText(getModel().getText()::setLocationOpenInMap, AppConstants.PATIENT_LOCATION_OPEN_IN_MAPS);
        setText(getModel().getText()::setMissionStart, AppConstants.MISSION_START_DATE);
        setText(getModel().getText()::setMissionEnd, AppConstants.MISSION_END_DATE);
        setText(getModel().getText()::setPatientMedication, AppConstants.PATIENT_MEDICATION);
        setText(getModel().getText()::setPatientDiagnoses, AppConstants.PATIENT_DIAGNOSES);
        setText(getModel().getText()::setPatientDoctor, AppConstants.PATIENT_DOCTOR);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void setMission(MissionDto missionDto) {
        if (missionDto == null)  {
            presenter.navigateToOverview();
            return;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        PatientDto patient = missionDto.getMissionSeries().getPatient();

        nameLabel.setText(patient.getDisplayName());
        startDateLabel.setText(missionDto.getStartDate().format(dateFormat));
        endDateLabel.setText(missionDto.getEndDate().format(dateFormat));


        String location = patient.getAddress().toString();
        locationLabel.setText(location);
        mapsLocationFrame.setSrc(getMapsUrlForAddress(location));

        List<DiagnoseDto> diagnoses = patient.getDiagnoses();
        diagnosesDiv.removeAll();
        if (diagnoses.isEmpty()) {
            diagnosesDiv.add(new Div(new Label(getTranslation(AppConstants.PATIENT_NO_DIAGNOSE.getKey()))));
        } else {
            diagnoses.forEach((x) -> {
                diagnosesDiv.add(new Div(new Label(x.getName())));
            });
        }

        List<MedicationDto> medications = patient.getMedications();
        medicationDiv.removeAll();
        if (medications.isEmpty()) {
            medicationDiv.add(new Div(new Label(getTranslation(AppConstants.PATIENT_NO_MEDICATION.getKey()))));
        } else {
            medications.forEach((x) -> {
                medicationDiv.add(new Div(new Label(x.getName() + " (" + x.getUsage() + ")")));
            });
        }

        DoctorDto doctor = patient.getDoctor();
        doctorLabel.setText(doctor.getTitle() + " " + doctor.getFirstname() + " " + doctor.getLastname());
    }

    private String getMapsUrlForAddress(String location) {
		String apiKey = 'BLA';
        String baseUrl = "http://www.google.com/maps/embed/v1/place?key=" + apiKey;
        return baseUrl + "&q=" + location.replace(" ", "+") + "";
    }

    @Override
    public void setStartButtonEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
    }

    @Override
    public void setStopButtonEnabled(boolean enabled) {
        finishButton.setEnabled(enabled);
    }

    @Override
    public void setShowReportVisibility(boolean visible) {
        showReportButton.setVisible(visible);
    }


    @EventHandler
    private void startButtonPressed() {
        presenter.onStartButtonPressed();
    }

    @EventHandler
    private void finishButtonPressed() {
        presenter.onFinishButtonPressed();
    }

    @EventHandler
    private void showReportButtonPressed() {
        presenter.onShowReportPressed();
    }

}
