package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.confirmation;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@HtmlImport("src/frontoffice/report/ReportConfirmationViewImpl.html")
@Tag("report-confirmation")
@Component
@UIScope
public class ReportConfirmationViewImpl extends BaseViewImpl<ReportConfirmationModel> implements ReportConfirmationView {

    private Presenter presenter;

    @Autowired
    public ReportConfirmationViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.REPORT_CONFIRMATION);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
