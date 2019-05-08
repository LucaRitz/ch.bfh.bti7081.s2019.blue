package ch.bfh.bti7081.s2019.blue;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstantsProvider;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.inject.Singleton;

@Configuration
@EnableVaadin
@ComponentScan("ch.bfh.bti7081.s2019.blue.client")
@ComponentScan("ch.bfh.bti7081.s2019.blue.server")
@EnableJpaRepositories("ch.bfh.bti7081.s2019.blue.server.persistence")
public class AppConfiguration {

    @Bean
    @Singleton
    public ServerConstants getServerConstants(ServerConstantsProvider provider) {
        return provider.get();
    }
}
