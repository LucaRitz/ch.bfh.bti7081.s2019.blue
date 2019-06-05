package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.action;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.ActionDto;

import java.util.List;

public interface ReportActionsView extends IsView {

    void setActions(List<ActionDto> actions);

    List<ActionDto> getActions();

    boolean validate();
}
