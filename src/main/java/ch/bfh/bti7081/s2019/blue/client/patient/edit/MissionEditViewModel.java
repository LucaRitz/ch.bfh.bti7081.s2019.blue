package ch.bfh.bti7081.s2019.blue.client.patient.edit;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface MissionEditViewModel extends TemplateModel {

    Text getText();

    interface Text {
        void setEndDate(String endDate);
        void setSave(String save);
        void setCancel(String cancel);
    }
}
