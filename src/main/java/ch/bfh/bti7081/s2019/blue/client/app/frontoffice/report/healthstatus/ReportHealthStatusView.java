package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.healthstatus;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;

public interface ReportHealthStatusView extends IsView {

    void setPresenter(Presenter presenter);

    interface Presenter {
    }
}
