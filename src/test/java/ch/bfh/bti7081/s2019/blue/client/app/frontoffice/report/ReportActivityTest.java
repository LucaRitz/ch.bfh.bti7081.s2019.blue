package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsRouter;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsSessionHandler;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.action.ReportActionsActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.confirmation.ReportConfirmationActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.duration.ReportDurationActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.feedback.ReportFeedbackActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.healthstatus.ReportHealthStatusActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks.ReportTasksActivity;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.client.rest.Promise;
import ch.bfh.bti7081.s2019.blue.client.rest.Promises;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeService;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionService;
import ch.bfh.bti7081.s2019.blue.client.ws.PatientService;
import ch.bfh.bti7081.s2019.blue.client.ws.ReportService;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportActivityTest {

    private static final Random RAND = new Random();

    private ReportActivity activity;
    @Mock
    private ReportView view;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PatientService patientService;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private MissionService missionService;
    @Mock
    private ReportService reportService;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private EmployeeService employeeService;
    @Mock
    private ReportTasksActivity tasksActivity;
    @Mock
    private ReportActionsActivity actionsActivity;
    @Mock
    private ReportFeedbackActivity feedbackActivity;
    @Mock
    private ReportHealthStatusActivity healthStatusActivity;
    @Mock
    private ReportDurationActivity durationActivity;
    @Mock
    private IsRouter router;
    @Mock
    private ReportConfirmationActivity confirmationActivity;
    @Mock
    private IsSessionHandler sessionHandler;


    @BeforeEach
    void setUp() {
        activity = new ReportActivity(view, patientService, missionService,
                reportService, employeeService, router, tasksActivity, actionsActivity,
                healthStatusActivity, durationActivity, feedbackActivity, confirmationActivity, sessionHandler);
    }

    @Test
    void start_getReportCorrectly() {
        int expectedMissionId = RAND.nextInt();
        activity.setMissionId(expectedMissionId);

        // Act
        activity.start();

        // Assert
        verify(missionService).getReport(expectedMissionId);
    }

    @Test
    void start_reportFound_showErrorMessageAndHideButtons() {
        when(missionService.getReport(any())).thenReturn(Promises.fulfill(new ReportDto()));

        // Act
        activity.start();

        // Assert
        verify(view).showNotification(AppConstants.REPORT_ALREADY_EXISTING.getKey());
    }

    @Test
    void start_reportNotFound_createReport() {
        int expectedMissionId = RAND.nextInt();
        activity.setMissionId(expectedMissionId);
        when(missionService.getReport(any())).thenReturn(Promises.fulfill(null));

        activity = spy(activity);

        // Act
        activity.start();

        // Assert
        verify(activity).createReport(expectedMissionId);
    }

    @Test
    void start_reportCreated_show() {
        ReportDto expectedReport = new ReportDto();
        when(missionService.getReport(any())).thenReturn(Promises.fulfill(null));

        activity = spy(activity);
        doReturn(Promises.fulfill(expectedReport)).when(activity).createReport(any());

        // Act
        activity.start();

        // Assert
        verify(activity).show(expectedReport);
    }

    @Test
    void show_startAllSteps() {
        // Act
        activity.show(new ReportDto());

        // Assert
        verify(tasksActivity).start();
        verify(actionsActivity).start();
        verify(feedbackActivity).start();
        verify(healthStatusActivity).start();
        verify(durationActivity).start();
    }

    @Test
    void show_setStepViews() {
        IsView expectedView = mock(IsView.class);

        activity = spy(activity);
        doReturn(Collections.singletonList(expectedView)).when(activity).getWizardStepViews();

        // Act
        activity.show(new ReportDto());

        // Assert
        verify(view).setStepViews(Collections.singletonList(expectedView));
    }

    @Test
    void show_updateActions() {
        activity = spy(activity);

        // Act
        activity.show(new ReportDto());

        // Assert
        verify(activity).updateActions();
    }

    @Test
    void createReport_callMissionServiceCorrectly() {
        int expectedMissionId = RAND.nextInt();

        // Act
        activity.createReport(expectedMissionId);

        // Assert
        verify(missionService).get(expectedMissionId);
    }

    @Test
    void createReport_callPatientServiceCorrectly() {
        int expectedPatientId = RAND.nextInt();
        MissionDto dto = mock(MissionDto.class, RETURNS_DEEP_STUBS);
        when(dto.getMissionSeries().getPatient().getId()).thenReturn(expectedPatientId);
        when(missionService.get(any())).thenReturn(Promises.fulfill(dto));

        // Act
        activity.createReport(RAND.nextInt());

        // Assert
        verify(patientService).get(expectedPatientId);
    }
}
