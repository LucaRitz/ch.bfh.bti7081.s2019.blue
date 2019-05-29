package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.shared.dto.AddressRefDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;

import java.time.format.DateTimeFormatter;

public class MissionItem extends ListItem {

    public MissionItem(boolean isDone, MissionDto mission) {

        PatientRefDto patient = mission.getMissionSeries().getPatient();
        String firstname = patient.getFirstname();
        String lastname = patient.getLastname();

        AddressRefDto address = patient.getAddress();
        String streetName = address.getStreetName();
        String houseNumber = address.getHouseNr();
        String postalCode = address.getPostalCode().toString();
        String city = address.getCity();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("hh:mm");
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

        this.add(timeWithPatientName);
        this.add(addressOfPatient);
        this.add(detailsButton);

        if (isDone) {
            this.getClassNames().add("done");
        }
    }
}
