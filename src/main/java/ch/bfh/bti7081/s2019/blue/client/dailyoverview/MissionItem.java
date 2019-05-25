package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;

public class MissionItem extends ListItem {

    public MissionItem(boolean isDone, String firstname, String lastname, String streetName, String houseNumber,
                       String postalCode, String city, String startTime, String endTime) {
        Paragraph timeWithPatientName = new Paragraph();
        timeWithPatientName.add(startTime + "-" + endTime + " " + lastname + " " + firstname);
        Paragraph addressOfPatient = new Paragraph();
        addressOfPatient.add(streetName + " " + houseNumber + " , " + postalCode + " " + city);
        this.add(timeWithPatientName);
        this.add(addressOfPatient);
        if (isDone) {
            this.getClassNames().add("done");
        }
    }
}
