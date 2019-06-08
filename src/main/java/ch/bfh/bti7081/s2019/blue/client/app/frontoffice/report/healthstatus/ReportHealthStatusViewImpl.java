package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.healthstatus;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.HealthStatusDto;
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
    private NumberField physicalScore;

    @Id
    private NumberField mentalScore;

    @Autowired
    public ReportHealthStatusViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.REPORT_HEALTHSTATUS);
        setText(getModel().getText()::setPhysicalText, AppConstants.REPORT_HEALTHSTATUS_PHYSICAL);
        setText(getModel().getText()::setMentalText, AppConstants.REPORT_HEALTHSTATUS_MENTAL);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setHealthStatus(HealthStatusDto healthStatus) {
        this.physicalScore.setValue((double) healthStatus.getPhysicalScore());
        this.mentalScore.setValue((double) healthStatus.getMentalScore());
    }

    @Override
    public HealthStatusDto getHealthStatus() {
        HealthStatusDto dto = new HealthStatusDto();
        dto.setPhysicalScore(this.physicalScore.getValue().intValue());
        dto.setMentalScore(this.mentalScore.getValue().intValue());
        return dto;
    }

}
