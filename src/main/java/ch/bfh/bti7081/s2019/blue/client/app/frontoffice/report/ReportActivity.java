package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.action.ReportActionsActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.confirmation.ReportConfirmationActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks.ReportTasksActivity;
import ch.bfh.bti7081.s2019.blue.client.ws.PatientService;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.TaskTemplate;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.TaskDto;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@UIScope
public class ReportActivity extends BaseActivity implements ReportView.Presenter {

    private final ReportView view;

    private final PatientService patientService;
    private final Wizard<ReportStepActivity> wizard;
    private ReportDto report;

    @Autowired
    public ReportActivity(ReportView view,
                          PatientService patientService, ReportTasksActivity tasksActivity,
                          ReportActionsActivity actionsActivity,
                          ReportConfirmationActivity confirmationActivity) {
        this.view = view;
        this.patientService = patientService;
        this.view.setPresenter(this);
        this.wizard = new Wizard<>(Arrays.asList(tasksActivity, actionsActivity, confirmationActivity));
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        for (ReportStepActivity step : wizard.getSteps()) {
            step.start();
        }
        view.setStepViews(wizard.getSteps().stream()
                .map(BaseActivity::getView)
                .collect(Collectors.toList()));

        TaskDto task1 = new TaskDto(); // TODO: remove this
        task1.setDescription("Designen");
        task1.setDone(true);

        TaskDto task2 = new TaskDto(); // TODO: remove this
        task2.setDescription("Implementieren");
        task2.setDone(true);

        report = new ReportDto();
        report.setTasks(Arrays.asList(task1, task2));
        // TODO: set necessary information from mission

        wizard.getCurrent().setValue(report);
        show(wizard.getCurrent());
        updateActions();
    }

    public void setMissionId(Integer missionId) {
        Notification.show("Mission-ID: " + missionId);
        // TODO: create ReportDto and pass it to the first step-activity
        ReportDto report = new ReportDto();

//        patientService.get(report.getMission().getMissionSeries().getPatient().getId()).done(patient -> {
//
//            List<TaskDto> tasks = patient.getTaskTemplates().stream()
//                    .map(this::getTaskFromTemplate)
//                    .collect(Collectors.toList());
//
//            report.setTasks(tasks);
//        });
    }

    private TaskDto getTaskFromTemplate(TaskTemplate taskTemplate) {
        TaskDto task = new TaskDto();
        task.setDescription(taskTemplate.getDescription());
        task.setDone(true);
        return task;
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

    private void show(ReportStepActivity activity) {
        view.setCurrentStepView(activity.getView(), activity.getTitleKey());
    }

    private void updateActions() {
        view.setPreviousButtonEnabled(!wizard.isFirst());
        view.setNextButtonEnabled(!wizard.isLast());
    }

}
