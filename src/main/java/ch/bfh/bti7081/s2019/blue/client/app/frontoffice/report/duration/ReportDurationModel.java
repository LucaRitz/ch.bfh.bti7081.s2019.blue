package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.duration;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface ReportDurationModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
        void setDuration(String duration);
    }
}
