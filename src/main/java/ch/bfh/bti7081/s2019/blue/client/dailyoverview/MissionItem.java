package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;

public class MissionItem extends ListItem {

    public MissionItem(boolean isDone, String firstname, String lastname, String address, String startTime, String endTime) {
        Paragraph timeWithPatientName = new Paragraph();
        timeWithPatientName.add(startTime + "-" + endTime + " " + lastname + " " + firstname);
        Paragraph addressOfPatient = new Paragraph();
        addressOfPatient.add(address);
        this.add(timeWithPatientName);
        this.add(address);
        if (isDone) {
            this.getClassNames().add("done");
        }
    }
}
