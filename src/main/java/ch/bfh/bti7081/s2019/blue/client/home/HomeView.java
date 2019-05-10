package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;

public interface HomeView extends IsView {

    void setPresenter(Presenter presenter);

    void setData(HomeDto text);

    interface Presenter {
        void update(HomeDto homeDto);
    }
}
