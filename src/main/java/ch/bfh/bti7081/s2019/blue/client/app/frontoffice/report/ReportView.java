package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;

import java.util.List;

public interface ReportView extends IsView {

    void setPresenter(Presenter presenter);

    void setStepViews(List<IsView>stepViews);

    void setCurrentStepView(IsView view, AppConstants titleKey);

    void setPreviousButtonEnabled(boolean enabled);

    void setNextButtonEnabled(boolean enabled);

    void showSaveButton(boolean visible);

    void showBackToOverviewButton();

    void hideBackToOverviewButton();

    void setConfirmationView(IsView view);

    interface Presenter {

        void onNextStepButtonPressed();
        void onPreviousStepButtonPressed();
        void onSaveButtonPressed();
        void onBackToOverviewButtonPressed();
    }
}
