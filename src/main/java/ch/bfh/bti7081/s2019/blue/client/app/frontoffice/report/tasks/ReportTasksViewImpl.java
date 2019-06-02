package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@HtmlImport("src/frontoffice/report/ReportTasksViewImpl.html")
@Tag("report-tasks")
@Component
@UIScope
public class ReportTasksViewImpl extends BaseViewImpl<ReportTasksModel> implements ReportTasksView {

    private Presenter presenter;

    @Id
    private CheckboxGroup<String> taskCheckboxes;
    @Id
    private TextField taskDescription;

    @Autowired
    public ReportTasksViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.REPORT_TASKS);
        setText(getModel().getText()::setDescription, AppConstants.REPORT_TASKS_DESCRIPTION);
        setText(getModel().getText()::setAdd, AppConstants.ACTION_ADD);
        setText(getModel().getText()::setTaskDescription, AppConstants.REPORT_TASKS_TASKDESCRIPTION);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTasks(List<String> tasks) {
        taskCheckboxes.setDataProvider(DataProvider.ofCollection(tasks));
        taskCheckboxes.setItemLabelGenerator(item -> item);
    }

    @Override
    public boolean validate() {
        Set<String> tasks = taskCheckboxes.getSelectedItems();
        if (tasks.isEmpty()) {
            showNotification(AppConstants.REPORT_TASKS_AT_LEAST_ONE_TASK.getKey(), 3000);
            return false;
        }
        return true;
    }

    @EventHandler
    private void newTaskButtonPressed() {

        if (validateDescription()) {
            String description = taskDescription.getValue();
            // TODO: store new Task
            taskDescription.clear();
        }
    }

    private boolean validateDescription() {
        String description = taskDescription.getValue();
        final boolean invalid = description == null || description.isEmpty();
        taskDescription.setInvalid(invalid);
        taskDescription.setErrorMessage(getTranslation(AppConstants.REQUIRED.getKey()));
        return !invalid;
    }

}
