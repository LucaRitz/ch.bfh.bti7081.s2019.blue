package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.healthstatus;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.ReportStepActivity;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ReportHealthStatusActivity extends ReportStepActivity implements ReportHealthStatusView.Presenter {

    private final ReportHealthStatusView view;
    private ReportDto report;

    @Autowired
    public ReportHealthStatusActivity(ReportHealthStatusView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {

    }

    @Override
    public AppConstants getTitleKey() {
        return AppConstants.REPORT_HEALTHSTATUS;
    }

    @Override
    public void setValue(ReportDto report) {
        this.report = report;
        this.view.setHealthStatus(report.getHealthStatus());
    }

    @Override
    public ReportDto getValue() {
        this.report.setHealthStatus(view.getHealthStatus());
        return report;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
