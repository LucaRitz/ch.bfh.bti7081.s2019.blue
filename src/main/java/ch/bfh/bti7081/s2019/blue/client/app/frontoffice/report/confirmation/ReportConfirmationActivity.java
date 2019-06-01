package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.confirmation;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.ReportStepActivity;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ReportConfirmationActivity extends ReportStepActivity implements ReportConfirmationView.Presenter {

    private final ReportConfirmationView view;

    @Autowired
    public ReportConfirmationActivity(ReportConfirmationView view) {
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
}
