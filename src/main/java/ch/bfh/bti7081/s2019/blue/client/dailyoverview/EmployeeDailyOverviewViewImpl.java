package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@HtmlImport("src/EmployeeDailyOverviewViewImpl.html")
@Tag("employee-daily-overview")
@Component
@UIScope
public class EmployeeDailyOverviewViewImpl extends BaseViewImpl<EmployeeDailyOverviewModel> implements EmployeeDailyOverviewView {

    @Id
    private ComboBox<String> employees;

    @Id
    private UnorderedList missionList;

    private Presenter presenter;

    @Autowired
    public EmployeeDailyOverviewViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.MENU_EMPLOYEEDAILYOVERVIEW);
        MissionItem test = new MissionItem(true, "Terard", "Rickner", "am Arschlochwäg 7", "10:00", "12:00");
        ListItem test2 = new MissionItem(false, "Terard", "Rickner", "am Arschlochwäg 7", "10:00", "12:00");
        ListItem test3 = new MissionItem(false, "Terard", "Rickner", "am Arschlochwäg 7", "10:00", "12:00");
        missionList.add(test);
        missionList.add(test2);
        missionList.add(test3);
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @EventHandler
    private void StartMissionButtonPressed() {
        presenter.onStartMissionClicked();
    }
}
