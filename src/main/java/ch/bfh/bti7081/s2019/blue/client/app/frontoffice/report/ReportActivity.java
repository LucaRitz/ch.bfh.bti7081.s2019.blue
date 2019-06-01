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
import java.util.stream.Collectors;

@Component
@UIScope
public class ReportActivity extends BaseActivity implements ReportView.Presenter {

    private final ReportView view;

    private final Wizard<ReportStepActivity> wizard;

    @Autowired
    public ReportActivity(ReportView view,
                          ReportTasksActivity tasksActivity,
                          ReportConfirmationActivity confirmationActivity) {
        this.view = view;
        this.view.setPresenter(this);
        this.wizard = new Wizard<>(Arrays.asList(tasksActivity, confirmationActivity));
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
        show(wizard.getCurrent());
        updateActions();
    }

    public void setMissionId(Integer missionId) {
        Notification.show("Mission-ID: " + missionId);
    }

    @Override
    public void onNextStepButtonPressed() {
        if (!wizard.getCurrent().validate()) {
            return;
        }
        wizard.next();
        show(wizard.getCurrent());
        updateActions();
    }

    @Override
    public void onPreviousStepButtonPressed() {
        wizard.previous();
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
