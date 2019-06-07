package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsRouter;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.dailyoverview.EmployeeDailyOverviewEntryPoint;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.action.ReportActionsActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.confirmation.ReportConfirmationActivity;
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
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.HealthStatusDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.TaskDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.TaskTemplateDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@UIScope
public class ReportActivity extends BaseActivity implements ReportView.Presenter {

    private final ReportView view;
    private final PatientService patientService;
    private final MissionService missionService;
    private final ReportService reportService;
    private final EmployeeService employeeService;
    private final Wizard<ReportStepActivity> wizard;
    private final ReportConfirmationActivity confirmationActivity;
    private final IsRouter router;

    private ReportDto report;
    private Integer missionId;

    @Autowired
    public ReportActivity(ReportView view,
                          PatientService patientService,
                          MissionService missionService,
                          ReportService reportService,
                          EmployeeService employeeService,
                          IsRouter router,
                          ReportTasksActivity tasksActivity,
                          ReportActionsActivity actionsActivity,
                          ReportHealthStatusActivity healthStatusActivity,
                          ReportFeedbackActivity feedbackActivity,
                          ReportConfirmationActivity confirmationActivity) {
        this.view = view;
        this.patientService = patientService;
        this.missionService = missionService;
        this.reportService = reportService;
        this.employeeService = employeeService;
        this.confirmationActivity = confirmationActivity;
        this.router = router;
        this.view.setPresenter(this);
        this.wizard = new Wizard<>(Arrays.asList(healthStatusActivity, actionsActivity, tasksActivity, feedbackActivity));
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        missionService.getReport(missionId)
                .then(report -> {
                    if (report != null) {
                        view.showNotification(AppConstants.REPORT_ALREADY_EXISTING.getKey());
                        view.showBackToOverviewButton();
                        return Promises.reject(null);
                    } else {
                        return createReport(missionId);
                    }
                })
                .done(report -> {
                    this.report = report;
                    show(report);
                });
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    @Override
    public void onNextStepButtonPressed() {
        if (!wizard.getCurrent().validate()) {
            return;
        }
        report = wizard.getCurrent().getValue();
        wizard.next();
        wizard.getCurrent().setValue(report);
        show(wizard.getCurrent());
        updateActions();
    }

    @Override
    public void onPreviousStepButtonPressed() {
        report = wizard.getCurrent().getValue();
        wizard.previous();
        wizard.getCurrent().setValue(report);
        show(wizard.getCurrent());
        updateActions();
    }

    @Override
    public void onSaveButtonPressed() {
        report = wizard.getCurrent().getValue();
        reportService.create(report)
                .done(aVoid -> {
                    show(confirmationActivity);
                    view.showBackToOverviewButton();
                });
    }

    @Override
    public void onBackToOverviewButtonPressed() {
        router.navigate(EmployeeDailyOverviewEntryPoint.class);
    }

    @VisibleForTesting
    void show(ReportDto report) {
        for (ReportStepActivity step : wizard.getSteps()) {
            step.start();
        }
        confirmationActivity.start();
        view.setStepViews(getWizardStepViews());
        view.setConfirmationView(confirmationActivity.getView());

        wizard.getCurrent().setValue(report);
        show(wizard.getCurrent());
        updateActions();
    }

    @VisibleForTesting
    List<IsView> getWizardStepViews() {
        return wizard.getSteps().stream()
                .map(BaseActivity::getView)
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    Promise<ReportDto> createReport(Integer missionId) {
        ReportDto report = new ReportDto();
        report.setHealthStatus(new HealthStatusDto());
        return missionService.get(missionId)
                .then(missionDto -> {
                    report.setMission(missionDto);
                    return patientService.get(missionDto.getMissionSeries().getPatient().getId());
                })
                .then(patient -> {
                    report.setTasks(patient.getTaskTemplates().stream()
                            .map(this::getTaskFromTemplate)
                            .collect(Collectors.toList()));
                    return employeeService.find(EmployeeRole.BACKOFFICE_AGENT);
                })
                .then(agents -> {
                    if (agents.isEmpty()) {
                        view.showNotification(AppConstants.NO_AGENT_AVAILABLE.getKey());
                        return Promises.reject(null);
                    }
                    report.setBackofficeAgent(agents.get(0));
                    return Promises.fulfill(report);
                });
    }

    @VisibleForTesting
    void updateActions() {
        view.setPreviousButtonEnabled(!wizard.isFirst());
        view.setNextButtonEnabled(!wizard.isLast());
        view.showSaveButton(wizard.isLast());
        view.hideBackToOverviewButton();
    }

    private TaskDto getTaskFromTemplate(TaskTemplateDto taskTemplate) {
        TaskDto task = new TaskDto();
        task.setDescription(taskTemplate.getDescription());
        task.setDone(true);
        return task;
    }

    private void show(ReportStepActivity activity) {
        view.setCurrentStepView(activity.getView(), activity.getTitleKey());
    }
}
