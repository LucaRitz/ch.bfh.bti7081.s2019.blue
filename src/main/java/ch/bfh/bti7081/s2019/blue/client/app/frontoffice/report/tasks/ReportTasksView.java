package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.tasks;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.TaskDto;

import java.util.List;

public interface ReportTasksView extends IsView {

    void setPresenter(Presenter presenter);

    void setTasks(List<TaskDto> tasks);

    List<TaskDto> getTasks();

    boolean validate();

    interface Presenter {
    }
}
