package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.action;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface ReportActionsModel extends TemplateModel {

    Text getText();

    interface Text {
        void setTitle(String title);
        void setDescription(String description);
        void setAdd(String add);
        void setActionDescription(String taskDescription);
    }
}
