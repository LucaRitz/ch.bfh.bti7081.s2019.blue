package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;

public class MissionItem extends ListItem {

    public MissionItem(boolean isDone, String firstname, String lastname, String streetName, String houseNumber,
                       String postalCode, String city, String startTime, String endTime, String detailsButtonId,
                       String detailsButtonText) {
        Paragraph timeWithPatientName = new Paragraph();
        timeWithPatientName.add(startTime + " - " + endTime + " " + lastname + " " + firstname);
        Paragraph addressOfPatient = new Paragraph();
        addressOfPatient.add(streetName + " " + houseNumber + ", " + postalCode + " " + city);
        this.add(timeWithPatientName);
        this.add(addressOfPatient);
        Button detailsButton = new Button();
        detailsButton.setId(detailsButtonId);
        detailsButton.setText(detailsButtonText);
        this.add(detailsButton);

        if (isDone) {
            this.getClassNames().add("done");
        }
    }
}
