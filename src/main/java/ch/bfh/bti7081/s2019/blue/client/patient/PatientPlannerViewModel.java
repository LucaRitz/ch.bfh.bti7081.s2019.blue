package ch.bfh.bti7081.s2019.blue.client.patient;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface PatientPlannerViewModel extends TemplateModel {

    Text getText();

    interface Text {
        void setTitle(String title);
    }
}
