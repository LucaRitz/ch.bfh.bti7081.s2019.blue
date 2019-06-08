package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.duration;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;

import java.time.Duration;

public interface ReportDurationView extends IsView {

    void setPresenter(Presenter presenter);

    void setDuration(Duration duration);

    Duration getDuration();

    interface Presenter {
    }
}
