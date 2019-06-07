package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.feedback;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.ReportStepActivity;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ReportFeedbackActivity extends ReportStepActivity implements ReportFeedbackView.Presenter {

    private final ReportFeedbackView view;
    private ReportDto report;

    @Autowired
    public ReportFeedbackActivity(ReportFeedbackView view) {
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
        return AppConstants.REPORT_FEEDBACK;
    }

    @Override
    public void setValue(ReportDto report) {
        this.report = report;
        view.setFeedback(report.getComment());
    }

    @Override
    public ReportDto getValue() {
        this.report.setComment(view.getFeedback());
        return this.report;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
