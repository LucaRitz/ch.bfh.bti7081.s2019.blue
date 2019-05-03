package ch.bfh.bti7081.s2019.blue.server.service.home;

import ch.bfh.bti7081.s2019.blue.server.persistence.HomeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.builder.HomeBuilder;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Home;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import ch.bfh.bti7081.s2019.blue.shared.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HomeServiceImpl implements HomeService {

    private final HomeRepository repository;

    @Autowired
    public HomeServiceImpl(HomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public HomeDto get() {
        Example<Home> statement = Example.of(new HomeBuilder().setReference(10L).build());
        Optional<Home> home = repository.findOne(statement);

        if(home.isPresent()) {
            Home entity = home.get();
            HomeDto dto = new HomeDto();
            dto.setText(entity.getText());
            return dto;
        }
        return new HomeDto();
    }
}
