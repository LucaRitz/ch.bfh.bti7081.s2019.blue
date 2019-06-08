package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.duration;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.ReportStepActivity;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ReportDurationActivity extends ReportStepActivity implements ReportDurationView.Presenter {

    private final ReportDurationView view;
    private ReportDto report;

    @Autowired
    public ReportDurationActivity(ReportDurationView view) {
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
        return AppConstants.REPORT_DURATION;
    }

    @Override
    public void setValue(ReportDto report) {
        this.report = report;
    }

    @Override
    public ReportDto getValue() {
        return report;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
