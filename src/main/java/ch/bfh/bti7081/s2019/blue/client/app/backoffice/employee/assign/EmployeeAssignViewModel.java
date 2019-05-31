package ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee.assign;
import com.vaadin.flow.templatemodel.TemplateModel;

public interface EmployeeAssignViewModel extends TemplateModel {
    Text getText();

    interface Text{
        void setMissions(String employee);
        void setSave(String save);
        void setCancel(String save);
    }
}