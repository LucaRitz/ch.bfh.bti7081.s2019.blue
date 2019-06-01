package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@HtmlImport("src/frontoffice/report/ReportTasksViewImpl.html")
@Tag("report-tasks")
@Component
@UIScope
public class ReportTasksViewImpl extends BaseViewImpl<ReportTasksModel> implements ReportTasksView {

    private Presenter presenter;

    @Autowired
    public ReportTasksViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.REPORT_TASKS);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
