package ch.bfh.bti7081.s2019.blue.client.employee;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface EmployeeViewModel extends TemplateModel {
   Text getText();

    interface Text {
        void setTitle(String title);
    }
}
