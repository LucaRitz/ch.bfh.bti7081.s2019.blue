package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report.duration;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@HtmlImport("src/frontoffice/report/ReportDurationViewImpl.html")
@Tag("report-duration")
@Component
@UIScope
public class ReportDurationViewImpl extends BaseViewImpl<ReportDurationModel> implements ReportDurationView {

    private Presenter presenter;

    @Id
    private Label time;

    @Id
    private Button plusButton;

    @Id
    private Button minusButton;

    private Duration duration;

    @Autowired
    public ReportDurationViewImpl() {
        this.duration = Duration.ZERO;
        setText(getModel().getText()::setTitle, AppConstants.REPORT_DURATION);
        setText(getModel().getText()::setDuration, AppConstants.REPORT_DURATION_DURATION);

        plusButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            addDuration();
            setTimeText();
        });
        minusButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            decreaseDuration();
            setTimeText();
        });
        setTimeText();
    }

    private void addDuration() {
        if (this.duration.compareTo(Duration.ofHours(12)) < 0) {
            this.duration = this.duration.plus(Duration.ofMinutes(15));
        } else {
            showNotification("report.duration.maximum");
        }
    }

    private void decreaseDuration() {
        if (this.duration.compareTo(Duration.ZERO) > 0) {
            this.duration = this.duration.minus(Duration.ofMinutes(15));
        } else {
            showNotification("report.duration.minimum");
        }
    }

    private void setTimeText() {
        this.time.setText(this.duration.toHoursPart() + "h " + this.duration.toMinutesPart() + "min");
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

}
