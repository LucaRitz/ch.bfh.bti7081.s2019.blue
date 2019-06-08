package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.healthstatus;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.HealthStatusDto;

public interface ReportHealthStatusView extends IsView {

    void setPresenter(Presenter presenter);

    void setHealthStatus(HealthStatusDto healthStatus);

    HealthStatusDto getHealthStatus();

    interface Presenter {
    }
}
