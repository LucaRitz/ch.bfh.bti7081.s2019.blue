package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface ReportTasksModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
    }
}
