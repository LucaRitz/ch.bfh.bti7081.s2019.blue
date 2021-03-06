package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@HtmlImport("src/frontoffice/report/ReportViewImpl.html")
@Tag("mission-report")
@Component
@UIScope
public class ReportViewImpl extends BaseViewImpl<ReportModel> implements ReportView {

    @Id
    private Div viewContainer;
    @Id
    private Button previousStepButton;
    @Id
    private Button nextStepButton;
    @Id
    private Button saveButton;

    @Id
    private Button backToOverviewButton;

    private Presenter presenter;

    @Autowired
    public ReportViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.REPORT);
        setText(getModel().getText()::setSave, AppConstants.ACTION_SAVE);
        setText(getModel().getText()::setBackToOverview, AppConstants.ACTION_BACK_TO_OVERVIEW);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setStepViews(List<IsView> stepViews) {
        for (IsView stepView : stepViews) {
            viewContainer.add(stepView.asComponent());
        }
    }

    @Override
    public void setCurrentStepView(IsView view, AppConstants titleKey) {
        viewContainer.getChildren()
                .forEach(child -> child.setVisible(false));
        view.asComponent().setVisible(true);
        setText(getModel().getText()::setSubtitle, titleKey);
    }

    @Override
    public void setPreviousButtonEnabled(boolean enabled) {
        previousStepButton.setEnabled(enabled);
    }

    @Override
    public void setNextButtonEnabled(boolean enabled) {
        nextStepButton.setEnabled(enabled);
    }

    @Override
    public void showSaveButton(boolean visible) {
        saveButton.setVisible(visible);
        nextStepButton.setVisible(!visible);
    }

    @Override
    public void showBackToOverviewButton() {
        previousStepButton.setVisible(false);
        nextStepButton.setVisible(false);
        saveButton.setVisible(false);
        backToOverviewButton.setVisible(true);
    }

    @Override
    public void hideBackToOverviewButton() {
        backToOverviewButton.setVisible(false);
    }

    @Override
    public void setConfirmationView(IsView view) {
        viewContainer.add(view.asComponent());
    }

    @EventHandler
    private void nextStepButtonPressed() {
        presenter.onNextStepButtonPressed();
    }

    @EventHandler
    private void previousStepButtonPressed() {
        presenter.onPreviousStepButtonPressed();
    }

    @EventHandler
    private void saveButtonPressed() {
        presenter.onSaveButtonPressed();
    }

    @EventHandler
    private void backToOverviewButtonPressed() {
        presenter.onBackToOverviewButtonPressed();
    }
}
