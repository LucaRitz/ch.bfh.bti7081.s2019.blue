package ch.bfh.bti7081.s2019.blue.client.app.patient.assign;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionSeriesService;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionService;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class MissionAssignDialog extends BaseActivity implements MissionAssignView.Presenter {

    private final MissionAssignView view;
    private final MissionService missionService;
    private final DialogFactory dialogFactory;
    private final MissionSeriesService missionSeriesService;

    @VisibleForTesting
    IsDialog dialog;

    private Listener listener;
    private MissionDto missionDto;


    @Autowired
    public MissionAssignDialog(MissionAssignView view, MissionService missionService,
                               MissionSeriesService missionSeriesService, DialogFactory dialogFactory) {
        this.view = view;
        this.view.setPresenter(this);
        this.missionService = missionService;
        this.missionSeriesService = missionSeriesService;
        this.dialogFactory = dialogFactory;
    }

    @Override
    public void start() {
        view.edit(missionDto);
        Integer missionSeriesId = missionDto.getMissionSeries().getId();
        missionSeriesService.employeeRecommendations(missionSeriesId)
                .find(missionDto.getStartDate(), missionDto.getEndDate())
                .done(view::setEmployees);

        dialog = dialogFactory.show(view);
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void onSaveClicked(MissionDto dto) {
        missionService.create(dto)
                .done(aVoid -> {
                    if (listener != null) {
                        listener.onSaved();
                    }
                    dialog.close();
                });
    }

    @Override
    public void onCancelClicked() {
        if (dialog != null)
            dialog.close();
    }

    public void setProperties(MissionDto missionDto) {
        this.missionDto = missionDto;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public interface Listener {
        void onSaved();
    }
}
