package ch.bfh.bti7081.s2019.blue.client.patient.edit;

import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;

import java.util.List;

public interface MissionEditView extends IsView {

    void setPresenter(Presenter presenter);

    void edit(MissionSeriesDto missionSeriesDto);

    interface Presenter {

        void onSaveClicked(MissionSeriesDto dto);

        void onCancelClicked();
    }
}
