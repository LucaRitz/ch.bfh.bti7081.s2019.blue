package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.healthstatus;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface ReportHealthStatusModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
        void setPhysicalStatus(String text);
        void setPsychologicalStatus(String text);
    }
}
