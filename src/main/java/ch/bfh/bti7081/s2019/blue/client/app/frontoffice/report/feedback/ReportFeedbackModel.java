package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.feedback;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface ReportFeedbackModel extends TemplateModel {
    Text getText();

    interface Text {
        void setFeedbackDescription(String description);    }
}
