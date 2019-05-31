package ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient;

import ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient.assign.MissionAssignDialog;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient.create.MissionCreateDialog;
import ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient.edit.MissionEditDialog;
import ch.bfh.bti7081.s2019.blue.client.rest.Promises;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionService;
import ch.bfh.bti7081.s2019.blue.client.ws.PatientService;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientPlannerActivityTest {

    private static final Random RAND = new Random();

    private PatientPlannerActivity activity;

    @Mock
    private PatientPlannerView view;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PatientService patientService;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private MissionService missionService;
    @Mock
    private MissionCreateDialog createDialog;
    @Mock
    private MissionEditDialog editDialog;
    @Mock
    private MissionAssignDialog missionAssignDialog;

    @BeforeEach
    void setUp() {
        activity = new PatientPlannerActivity(view, patientService, missionService, createDialog, editDialog, missionAssignDialog);
    }

    @Test
    void start_loadMasterdata() {
        activity = spy(activity);

        // Act
        activity.start();

        // Assert
        verify(activity).loadMasterdata();
    }

    @Test
    void onCreateClicked_showCreateDialog() {
        PatientRefDto expectedPatientRefDto = new PatientRefDto();

        when(view.getPatient()).thenReturn(expectedPatientRefDto);

        // Act
        activity.onCreateClicked();

        // Assert
        verify(createDialog).setProperties(expectedPatientRefDto);
        verify(createDialog).setListener(any());
        verify(createDialog).start();
    }

    @Test
    void onEditClicked_noMissionSelected_showNotification() {
        when(view.getSelectedMissionSeries()).thenReturn(null);

        // Act
        activity.onEditClicked();

        // Assert
        verify(view).showNotification(AppConstants.PATIENT_PLANNER_NO_SELECTED_MISSION.getKey());
    }

    @Test
    void onEditClicked_missionSelected_showEditDialog() {
        MissionSeriesDto expectedMissionSeriesDto = new MissionSeriesDto();

        when(view.getSelectedMissionSeries()).thenReturn(expectedMissionSeriesDto);

        // Act
        activity.onEditClicked();

        // Assert
        verify(editDialog).setProperties(expectedMissionSeriesDto);
        verify(editDialog).setListener(any());
        verify(editDialog).start();
    }

    @Test
    void onSelectionChange_callServiceCorrectly() {
        int expectedPatientNumber = RAND.nextInt();
        LocalDateTime expectedStartDate = LocalDateTime.now();
        LocalDateTime expectedEndDate = LocalDateTime.now();

        PatientRefDto patientRefDto = mock(PatientRefDto.class);
        when(patientRefDto.getNumber()).thenReturn(expectedPatientNumber);

        // Act
        activity.onSelectionChange(patientRefDto, expectedStartDate, expectedEndDate);

        // Assert
        verify(missionService).find(expectedPatientNumber, expectedStartDate, expectedEndDate);
    }

    @Test
    void onSelectionChange_updateMissionsOnView() {
        List<MissionDto> expectedMissions = Collections.singletonList(new MissionDto());

        when(missionService.find(any(), any(), any())).thenReturn(Promises.fulfill(expectedMissions));

        // Act
        activity.onSelectionChange(new PatientRefDto(), LocalDateTime.now(), LocalDateTime.now());

        // Assert
        verify(view).setMissions(expectedMissions);
    }

    @Test
    void loadMasterdata_updatePatientsOnView() {
        List<PatientRefDto> expectedPatients = Collections.singletonList(new PatientRefDto());
        when(patientService.get()).thenReturn(Promises.fulfill(expectedPatients));

        // Act
        activity.loadMasterdata();

        // Assert
        verify(view).setPatients(expectedPatients);
    }
}
