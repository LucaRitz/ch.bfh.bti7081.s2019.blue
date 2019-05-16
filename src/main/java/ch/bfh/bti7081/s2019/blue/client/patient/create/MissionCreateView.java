package ch.bfh.bti7081.s2019.blue.client.patient.create;

import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;

import java.util.List;

public interface MissionCreateView extends IsView {

    void setPresenter(Presenter presenter);

    void edit(MissionSeriesDto missionSeriesDto);

    void showErrors(List<String> errors);

    interface Presenter {

        void onSaveClicked(MissionSeriesDto dto);

        void onCancelClicked();
    }
}