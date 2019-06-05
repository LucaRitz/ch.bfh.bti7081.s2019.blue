package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.TaskDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@HtmlImport("src/frontoffice/report/ReportTasksViewImpl.html")
@Tag("report-tasks")
@Component
@UIScope
public class ReportTasksViewImpl extends BaseViewImpl<ReportTasksModel> implements ReportTasksView {

    private Presenter presenter;

    @Id
    private CheckboxGroup<TaskDto> taskCheckboxes;
    @Id
    private TextField taskDescription;

    private List<TaskDto> tasks = new ArrayList<>();

    @Autowired
    public ReportTasksViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.REPORT_TASKS);
        setText(getModel().getText()::setDescription, AppConstants.REPORT_TASKS_DESCRIPTION);
        setText(getModel().getText()::setAdd, AppConstants.ACTION_ADD);
        setText(getModel().getText()::setTaskDescription, AppConstants.REPORT_TASKS_TASKDESCRIPTION);

        taskCheckboxes.setItemLabelGenerator(TaskDto::getDescription);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTasks(List<TaskDto> tasks) {
        this.tasks = new ArrayList<>(tasks);
        taskCheckboxes.setDataProvider(DataProvider.ofCollection(this.tasks));

        for (TaskDto task : this.tasks) {
            if(task.isDone()) {
                taskCheckboxes.select(task);
            } else {
                taskCheckboxes.deselect(task);
            }
        }
    }

    @Override
    public List<TaskDto> getTasks() {
        for (TaskDto task : this.tasks) {
            task.setDone(taskCheckboxes.isSelected(task));
        }
        return new ArrayList<>(this.tasks);
    }

    @Override
    public boolean validate() {
        Set<TaskDto> tasks = taskCheckboxes.getSelectedItems();
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

            TaskDto task = new TaskDto();
            task.setDescription(description);
            task.setDone(true);

            addTask(task);
            taskDescription.clear();
        }
    }

    private void addTask(TaskDto task) {
        Set<TaskDto> selected = taskCheckboxes.getSelectedItems();
        this.tasks.add(task);
        // refreshAll deselects all items, so we need to select them again
        taskCheckboxes.getDataProvider().refreshAll();
        taskCheckboxes.select(selected);
        taskCheckboxes.select(task);
    }

    private boolean validateDescription() {
        String description = taskDescription.getValue();
        final boolean invalid = description == null || description.isEmpty();
        taskDescription.setInvalid(invalid);
        taskDescription.setErrorMessage(getTranslation(AppConstants.REQUIRED.getKey()));
        return !invalid;
    }

}
