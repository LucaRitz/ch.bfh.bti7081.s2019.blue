package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import com.vaadin.flow.templatemodel.TemplateModel;

public interface HomeViewModel extends TemplateModel {
    void setTitle(String title);

    HomeDto getHome();

    void setHome(HomeDto dto);
}
