package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.mission;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface MissionModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
        void setSubtitle(String subtitle);

        void setStartMission(String startMission);
        void setFinishMission(String finishMission);
        void setShowReport(String showReport);

        void setPatientName(String patientName);
        void setPatientLocation(String patientLocation);
        void setMissionStart(String missionStart);
        void setMissionEnd(String missionEnd);
        void setPatientMedication(String patientMedication);
        void setPatientDoctor(String patientDoctor);
        void setLocationOpenInMap(String locationOpenInMap);
    }
}
