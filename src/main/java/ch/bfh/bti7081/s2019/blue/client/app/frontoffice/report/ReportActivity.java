package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.confirmation.ReportConfirmationActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks.ReportTasksActivity;
import com.vaadin.flow.component.notification.Notification;
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
    private final ReportTasksActivity tasksActivity;
    private final ReportConfirmationActivity confirmationActivity;

    private List<ReportStepActivity> steps;
    private int currentStepIndex = 0;

    @Autowired
    public ReportActivity(ReportView view,
                          ReportTasksActivity tasksActivity,
                          ReportConfirmationActivity confirmationActivity) {
        this.view = view;
        this.view.setPresenter(this);
        this.tasksActivity = tasksActivity;
        this.confirmationActivity = confirmationActivity;
        this.steps = Arrays.asList(tasksActivity, confirmationActivity);
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        view.setStepViews(steps.stream()
                .map(BaseActivity::getView)
                .collect(Collectors.toList()));
        show(currentStepIndex);
    }

    public void setMissionId(Integer missionId) {
        Notification.show("Mission-ID: " + missionId);
    }

    @Override
    public void onNextStepButtonPressed() {
        this.currentStepIndex = this.currentStepIndex + 1 >= steps.size() ? 0 : this.currentStepIndex + 1;
        show(currentStepIndex);
    }

    @Override
    public void onPreviousStepButtonPressed() {
        this.currentStepIndex = this.currentStepIndex - 1 < 0 ? steps.size() - 1 : this.currentStepIndex - 1;
        show(currentStepIndex);
    }

    private void show(int stepIndex) {
        view.setCurrentStepView(steps.get(stepIndex).getView());
    }
}
