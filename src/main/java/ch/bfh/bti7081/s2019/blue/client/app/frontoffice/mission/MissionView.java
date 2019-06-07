package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.mission;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;

public interface MissionView extends IsView {

    void setPresenter(Presenter presenter);

    void setMission(MissionDto missionDto);

    void setStartButtonEnabled(boolean enabled);
    void setStopButtonEnabled(boolean enabled);

    interface Presenter {

        void onStartButtonPressed();
        void onFinishButtonPressed();

        void navigateToOverview();
    }
}
