package ch.bfh.bti7081.s2019.blue.client.dailyoverview;

import ch.bfh.bti7081.s2019.blue.client.base.IsView;

public interface EmployeeDailyOverviewView extends IsView {

    void setPresenter(Presenter presenter);

    interface Presenter {
        void onStartMissionClicked();
    }
}
