package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.action;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.ActionDto;
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

@HtmlImport("src/frontoffice/report/ReportActionsViewImpl.html")
@Tag("report-actions")
@Component
@UIScope
public class ReportActionsViewImpl extends BaseViewImpl<ReportActionsModel> implements ReportActionsView {

    @Id
    private CheckboxGroup<ActionDto> actionCheckboxes;
    @Id
    private TextField actionDescription;

    private List<ActionDto> actions = new ArrayList<>();

    @Autowired
    public ReportActionsViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.REPORT_ACTIONS);
        setText(getModel().getText()::setDescription, AppConstants.REPORT_ACTIONS_DESCRIPTION);
        setText(getModel().getText()::setAdd, AppConstants.ACTION_ADD);
        setText(getModel().getText()::setActionDescription, AppConstants.REPORT_ACTIONS_ACTIONDESCRIPTION);

        actionCheckboxes.setItemLabelGenerator(ActionDto::getName);
    }

    @Override
    public void setActions(List<ActionDto> actions) {
        this.actions = new ArrayList<>(actions);
        actionCheckboxes.setDataProvider(DataProvider.ofCollection(this.actions));

        for (ActionDto task : this.actions) {
            if (task.isDone()) {
                actionCheckboxes.select(task);
            } else {
                actionCheckboxes.deselect(task);
            }
        }
    }

    @Override
    public List<ActionDto> getActions() {
        for (ActionDto action : this.actions) {
            action.setDone(actionCheckboxes.isSelected(action));
        }
        return new ArrayList<>(this.actions);
    }

    @Override
    public boolean validate() {
        Set<ActionDto> actions = actionCheckboxes.getSelectedItems();
        if (actions.isEmpty()) {
            showNotification(AppConstants.REPORT_ACTIONS_AT_LEAST_ONE_TASK.getKey(), 3000);
            return false;
        }
        return true;
    }

    @EventHandler
    private void newActionButtonPressed() {
        if (validateDescription()) {
            String description = actionDescription.getValue();

            ActionDto action = new ActionDto();
            action.setName(description);
            action.setDone(true);

            addAction(action);
            actionDescription.clear();
        }
    }

    private void addAction(ActionDto action) {
        Set<ActionDto> selected = actionCheckboxes.getSelectedItems();
        this.actions.add(action);
        // refreshAll deselects all items, so we need to select them again
        actionCheckboxes.getDataProvider().refreshAll();
        actionCheckboxes.select(selected);
        actionCheckboxes.select(action);
    }

    private boolean validateDescription() {
        String description = actionDescription.getValue();
        final boolean invalid = description == null || description.isEmpty();
        actionDescription.setInvalid(invalid);
        actionDescription.setErrorMessage(getTranslation(AppConstants.REQUIRED.getKey()));
        return !invalid;
    }
}
