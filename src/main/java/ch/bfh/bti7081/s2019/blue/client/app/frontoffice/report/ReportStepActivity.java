package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;

public abstract class ReportStepActivity extends BaseActivity {

    public abstract AppConstants getTitleKey();

    public abstract void setValue(ReportDto report);

    public abstract ReportDto getValue();

    public abstract boolean validate();
}
