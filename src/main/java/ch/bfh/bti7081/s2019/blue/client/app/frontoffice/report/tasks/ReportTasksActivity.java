package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.ReportStepActivity;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@UIScope
public class ReportTasksActivity extends ReportStepActivity implements ReportTasksView.Presenter {

    private final ReportTasksView view;

    @Autowired
    public ReportTasksActivity(ReportTasksView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        view.setTasks(Arrays.asList("testen", "implementieren", "designen")); // TODO: replace with real data
    }

    @Override
    public AppConstants getTitleKey() {
        return AppConstants.REPORT_TASKS;
    }

    @Override
    public boolean validate() {
        return view.validate();
    }
}
