package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.mission;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsRouter;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsSessionHandler;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.dailyoverview.EmployeeDailyOverviewEntryPoint;
import ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.ReportEntryPoint;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.client.rest.Promises;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@UIScope
public class MissionActivity extends BaseActivity implements MissionView.Presenter {

    private final MissionView view;
    private final MissionService missionService;
    private final IsRouter router;
    private final IsSessionHandler sessionHandler;

    private Integer missionId;
    private boolean reportExists;

    @Autowired
    public MissionActivity(MissionView view, MissionService missionService, IsRouter router,
                           IsSessionHandler sessionHandler) {
        this.view = view;
        this.view.setPresenter(this);
        this.missionService = missionService;
        this.router = router;
        this.sessionHandler = sessionHandler;
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        loadViewModel();
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    @Override
    public void onStartButtonPressed() {
        sessionHandler.set(getMissionStartedKey(missionId), LocalDateTime.now());
        //view.showNotification("Mission successfully started", 5000);
        updateActions();
    }

    @Override
    public void onFinishButtonPressed() {
        navigateToReport();
    }

    @Override
    public void onShowReportPressed() {
        navigateToReport();
    }

    @Override
    public void navigateToOverview() {
        router.navigate(EmployeeDailyOverviewEntryPoint.class);
    }

    private void navigateToReport() {
        router.navigate(ReportEntryPoint.class, missionId);
    }

    @VisibleForTesting
    void loadViewModel() {
        if (missionId == null)  {
            System.err.println("missionId was null");
            navigateToOverview();
            return;
        }

        missionService.get(missionId)
                .done((missionDto) -> {
                    if (missionDto == null) {
                        navigateToOverview();
                        return;
                    }

                    missionService.getReport(missionId)
                            .done(report -> {
                                reportExists = report != null;

                                view.setMission(missionDto);
                                updateActions();
                            });
                });
    }

    private void updateActions() {

        if (reportExists) {
            view.setStartButtonEnabled(false);
            view.setStopButtonEnabled(false);
            view.setShowReportVisibility(true);
            return;
        }

        view.setShowReportVisibility(false);

        LocalDateTime timeStamp = sessionHandler.get(getMissionStartedKey(missionId));

        if (timeStamp != null) {
            view.setStartButtonEnabled(false);
            view.setStopButtonEnabled(true);
        } else {
            view.setStartButtonEnabled(true);
            view.setStopButtonEnabled(false);
        }
    }

    private String getMissionStartedKey(Integer missionId) {
        return  "mission_started_" + missionId;
    }
}
