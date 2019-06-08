package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.mission.MissionEntryPoint;
import ch.bfh.bti7081.s2019.blue.shared.dto.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;

import java.time.format.DateTimeFormatter;

public class MissionItem extends ListItem {

    public MissionItem(boolean isDone, MissionDto mission) {

        PatientDto patient = mission.getMissionSeries().getPatient();
        String firstname = patient.getFirstname();
        String lastname = patient.getLastname();

        AddressDto address = patient.getAddress();
        String streetName = address.getStreetName();
        String houseNumber = address.getHouseNr();
        String postalCode = address.getPostalCode().toString();
        String city = address.getCity();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm");
        String startTime = dateFormat.format(mission.getStartDate());
        String endTime = dateFormat.format(mission.getEndDate());

        String detailsButtonId = mission.getId().toString();
        String detailsButtonText = this.getTranslation("employee.dailyoverview.details");

        Paragraph timeWithPatientName = new Paragraph();
        timeWithPatientName.add(startTime + " - " + endTime + " " + lastname + " " + firstname);

        Paragraph addressOfPatient = new Paragraph();
        addressOfPatient.add(streetName + " " + houseNumber + ", " + postalCode + " " + city);

        Button detailsButton = new Button();
        detailsButton.setId(detailsButtonId);
        detailsButton.setText(detailsButtonText);
        detailsButton.addClickListener((event) -> UI.getCurrent().navigate(MissionEntryPoint.class, mission.getId()));

        this.add(timeWithPatientName);
        this.add(addressOfPatient);
        this.add(detailsButton);

        if (isDone) {
            this.getClassNames().add("done");
        }
    }
}
