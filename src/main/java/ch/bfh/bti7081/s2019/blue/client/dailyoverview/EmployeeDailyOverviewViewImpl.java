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
        this.employees.addValueChangeListener(event -> this.loadMissionEntries());
        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            this.setSelectedDate(-1);
            loadMissionEntries();
        });
        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            this.setSelectedDate(1);
            loadMissionEntries();
        });
        this.selectedDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void loadMissionEntries() {

        EmployeeDto selectedEmployee = employees.getValue();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yyyy");
        this.date.setText(dateFormat.format(this.selectedDate));
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
            showNotification("employee.dailyoverview.nomissions");
        } else {
            for (MissionDto mission: list) {

                MissionItem missionItem = new MissionItem(false, mission);
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
