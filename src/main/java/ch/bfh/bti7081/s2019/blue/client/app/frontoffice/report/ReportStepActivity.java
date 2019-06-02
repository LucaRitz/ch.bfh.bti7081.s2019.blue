package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;

public abstract class ReportStepActivity extends BaseActivity {

    public abstract AppConstants getTitleKey();

    public abstract boolean validate();
}
