package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.healthstatus;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@HtmlImport("src/frontoffice/report/ReportHealthStatusViewImpl.html")
@Tag("report-healthstatus")
@Component
@UIScope
public class ReportHealthStatusViewImpl extends BaseViewImpl<ReportHealthStatusModel> implements ReportHealthStatusView {

    private Presenter presenter;

    @Id
    private Paragraph physicalStatus;

    @Id
    private NumberField physicalStatusPoints;

    @Id
    private Paragraph psychologicalStatus;

    @Id
    private NumberField psychologicalStatusPoints;

    @Autowired
    public ReportHealthStatusViewImpl() {

        setText(getModel().getText()::setTitle, AppConstants.REPORT_HEALTHSTATUS);
        setText(getModel().getText()::setPhysicalStatus, AppConstants.REPORT_HEALTHSTATUS_PHYSICAL);
        setText(getModel().getText()::setPsychologicalStatus, AppConstants.REPORT_HEALTHSTATUS_PSYCHOLOGICAL);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
