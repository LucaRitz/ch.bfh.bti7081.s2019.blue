package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.feedback;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.TaskDto;

import java.util.List;

public interface ReportFeedbackView extends IsView {

    void setPresenter(Presenter presenter);

    void setFeedback(String feedback);

    String getFeedback();

    interface Presenter {
    }
}
