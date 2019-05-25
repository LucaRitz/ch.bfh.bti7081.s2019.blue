package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.AddressRefDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@HtmlImport("src/EmployeeDailyOverviewViewImpl.html")
@Tag("employee-daily-overview")
@Component
@UIScope
public class EmployeeDailyOverviewViewImpl extends BaseViewImpl<EmployeeDailyOverviewModel> implements EmployeeDailyOverviewView {

    @Id
    private Paragraph date;

    @Id
    private Button previousButton;
    @Id
    private Button nextButton;

    @Id
    private ComboBox<EmployeeDto> employees;

    @Id
    private UnorderedList missionList;

    private Presenter presenter;
    private Date selectedDate;

    @Autowired
    public EmployeeDailyOverviewViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.MENU_EMPLOYEEDAILYOVERVIEW);
        this.employees.setItemLabelGenerator((ItemLabelGenerator<EmployeeDto>)
                EmployeeDto::getDisplayName);
        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            this.setSelectedDate(-1);
            reloadEntries();
        });
        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            this.setSelectedDate(1);
            reloadEntries();
        });
        this.selectedDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.date.setText(this.selectedDate.toString());
    }

    private void reloadEntries() {

        EmployeeDto selectedEmployee = employees.getValue();
        this.date.setText(selectedDate.toString());
        Date nextDay = Date.from(this.selectedDate.toInstant().plusSeconds(24*3600));

        if (selectedEmployee != null) {
            presenter.onSelectionChange(selectedEmployee, selectedDate, nextDay);
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setEmployees(List<EmployeeDto> employees) {
        this.employees.setItems(employees);

        if (this.employees.getValue() == null && !employees.isEmpty()) {
            this.employees.setValue(employees.get(0));
        }
    }

    @Override
    public void setMissions(List<MissionDto> list) {
        this.missionList.removeAll();

        if (list.isEmpty()) {
            this.missionList.add("no entries found");
        } else {
            for (MissionDto mission: list) {
                PatientRefDto patient = mission.getMissionSeries().getPatient();
                String firstname = patient.getFirstname();
                String lastname = patient.getLastname();

                AddressRefDto address = patient.getAddress();
                String streetName = address.getStreetName();
                String houseNumber = address.getHouseNr();
                String postalCode = address.getPostalCode().toString();
                String city = address.getCity();

                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String startTime = dateFormat.format(mission.getStartDate());
                String endTime = dateFormat.format(mission.getEndDate());

                MissionItem missionItem = new MissionItem(false, firstname, lastname, streetName, houseNumber,
                                                            postalCode, city, startTime, endTime);
                missionList.add(missionItem);
            }
        }
    }

    @EventHandler
    private void onDetailsButtonPressed() {
        presenter.onDetailsClicked();
    }

    private void setSelectedDate(long difference) {
        this.selectedDate = Date.from(this.selectedDate.toInstant().plusSeconds(difference*24*3600));
    }
}
