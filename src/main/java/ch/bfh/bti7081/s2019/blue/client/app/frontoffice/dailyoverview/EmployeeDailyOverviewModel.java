package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.dailyoverview;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface EmployeeDailyOverviewModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
    }
}
