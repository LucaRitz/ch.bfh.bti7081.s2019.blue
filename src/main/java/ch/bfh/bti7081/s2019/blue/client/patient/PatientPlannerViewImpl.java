package ch.bfh.bti7081.s2019.blue.client.patient;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.stereotype.Component;

import java.util.List;

@HtmlImport("src/PatientPlannerViewImpl.html")
@Tag("patient-planner-view")
@Component
@UIScope
public class PatientPlannerViewImpl extends BaseViewImpl<TemplateModel> implements PatientPlannerView {

    @Id("patients")
    private ComboBox<PatientRefDto> patients;

    private Presenter presenter;

    public PatientPlannerViewImpl() {
        this.patients.setItemLabelGenerator((ItemLabelGenerator<PatientRefDto>) person -> person.getDisplayName() + ", " + person.getAge());
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPatients(List<PatientRefDto> patients) {

        this.patients.setItems(patients);
    }

}
