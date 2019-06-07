package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.feedback;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.TaskDto;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@HtmlImport("src/frontoffice/report/ReportFeedbackViewImpl.html")
@Tag("report-feedback")
@Component
@UIScope
public class ReportFeedbackViewImpl extends BaseViewImpl<ReportFeedbackModel> implements ReportFeedbackView {

    private Presenter presenter;

    @Id
    private TextArea feedbackTextArea;


    @Autowired
    public ReportFeedbackViewImpl() {
        setText(getModel().getText()::setFeedbackDescription, AppConstants.REPORT_FEEDBACK_DESCRIPTION);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setFeedback(String feedback) {
        if(feedback != null)
            feedbackTextArea.setValue(feedback);
    }

    @Override
    public String getFeedback() {
       return feedbackTextArea.getValue();
    }

}
