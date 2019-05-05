package ch.bfh.bti7081.s2019.blue;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableVaadin
@ComponentScans({
        @ComponentScan("ch.bfh.bti7081.s2019.blue.client"),
        @ComponentScan("ch.bfh.bti7081.s2019.blue.server")
})
@EnableJpaRepositories("ch.bfh.bti7081.s2019.blue.server.persistence")
public class AppConfiguration {

}
