package ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient.assign;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface MissionAssignViewModel extends TemplateModel {

    Text getText();

    interface Text{
        void setEmployee(String employee);
        void setSave(String save);
        void setCancel(String save);
    }
}
