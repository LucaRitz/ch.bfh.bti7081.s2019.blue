package ch.bfh.bti7081.s2019.blue.server.service.home;

import ch.bfh.bti7081.s2019.blue.server.persistence.HomeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Home;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import ch.bfh.bti7081.s2019.blue.shared.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeServiceImpl implements HomeService {

    private final HomeRepository repository;

    @Autowired
    public HomeServiceImpl(HomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public HomeDto get() {
        Home entity = repository.getOne(1L);
        HomeDto dto = new HomeDto();
        dto.setText(entity.getText());
        return dto;
    }
}
