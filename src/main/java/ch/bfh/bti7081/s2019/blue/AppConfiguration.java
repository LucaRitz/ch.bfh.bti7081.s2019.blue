package ch.bfh.bti7081.s2019.blue;

import ch.bfh.bti7081.s2019.blue.server.service.home.HomeServiceImpl;
import ch.bfh.bti7081.s2019.blue.shared.service.HomeService;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.inject.Singleton;

@Configuration
@EnableVaadin
@ComponentScans({
        @ComponentScan("ch.bfh.bti7081.s2019.blue.client"),
        @ComponentScan("ch.bfh.bti7081.s2019.blue.server")
})
@EnableJpaRepositories("ch.bfh.bti7081.s2019.blue.server.persistence")
public class AppConfiguration {

    @Bean
    @UIScope
    @Singleton
    HomeService homeService(HomeServiceImpl impl) {
        return impl;
    }
}
