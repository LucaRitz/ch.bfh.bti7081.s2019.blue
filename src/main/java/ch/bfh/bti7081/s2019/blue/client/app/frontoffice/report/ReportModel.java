package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface ReportModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
        void setSubtitle(String subtitle);
        void setSave(String save);
        void setBackToOverview(String save);
    }
}
