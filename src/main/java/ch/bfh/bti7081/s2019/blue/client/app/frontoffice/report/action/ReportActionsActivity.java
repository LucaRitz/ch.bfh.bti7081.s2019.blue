package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.action;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.ReportStepActivity;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks.ReportTasksView;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ReportActionsActivity extends ReportStepActivity implements ReportTasksView.Presenter {

    private final ReportActionsView view;

    private ReportDto report;

    @Autowired
    public ReportActionsActivity(ReportActionsView view) {
        this.view = view;
    }

    @Override
    public void start() {
        // NOOP
    }

    @Override
    public AppConstants getTitleKey() {
        return AppConstants.REPORT_ACTIONS;
    }

    @Override
    public void setValue(ReportDto report) {
        this.report = report;
        view.setActions(report.getActions());
    }

    @Override
    public ReportDto getValue() {
        this.report.setActions(view.getActions());
        return report;
    }

    @Override
    public boolean validate() {
        return view.validate();
    }

    @Override
    public IsView getView() {
        return view;
    }
}
