package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.confirmation;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface ReportConfirmationModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
    }
}
