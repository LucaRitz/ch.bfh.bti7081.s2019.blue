package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;

import java.util.List;

public interface ReportView extends IsView {

    void setPresenter(Presenter presenter);

    void setStepViews(List<IsView>stepViews);

    void setCurrentStepView(IsView view);

    interface Presenter {

        void onNextStepButtonPressed();
        void onPreviousStepButtonPressed();
    }
}
