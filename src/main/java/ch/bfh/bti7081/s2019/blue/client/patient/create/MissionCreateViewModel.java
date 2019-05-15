package ch.bfh.bti7081.s2019.blue.client.patient.create;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface MissionCreateViewModel extends TemplateModel {

    Text getText();

    interface Text {
        void setStartDate(String startDate);
        void setStartTime(String startTime);
        void setEndDate(String endDate);
        void setEndTime(String endTime);
        void setRepetitionType(String repetitionType);
        void setSave(String save);
        void setCancel(String cancel);
    }
}
