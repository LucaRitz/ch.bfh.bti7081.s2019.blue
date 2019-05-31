package ch.bfh.bti7081.s2019.blue.client.app.backoffice.patient.edit;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;

public interface MissionEditView extends IsView {

    void setPresenter(Presenter presenter);

    void edit(MissionSeriesDto missionSeriesDto);

    interface Presenter {

        void onSaveClicked(MissionSeriesDto dto);

        void onCancelClicked();
    }
}
