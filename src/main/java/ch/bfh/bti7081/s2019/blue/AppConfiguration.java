package ch.bfh.bti7081.s2019.blue;

import ch.bfh.bti7081.s2019.blue.client.rest.ProvidesConverter;
import ch.bfh.bti7081.s2019.blue.client.rest.RestResourceProxy;
import ch.bfh.bti7081.s2019.blue.client.ws.*;
import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstantsProvider;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.ResponseErrorHandler;

@Configuration
@EnableVaadin
@ComponentScan("ch.bfh.bti7081.s2019.blue.client")
@ComponentScan("ch.bfh.bti7081.s2019.blue.server")
@EnableJpaRepositories("ch.bfh.bti7081.s2019.blue.server.persistence")
public class AppConfiguration {

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ServerConstants getServerConstants(ServerConstantsProvider provider) {
        return provider.get();
    }

    // Rest Services
    @Bean
    public HomeService getHomeRestClient(ResponseErrorHandler errorHandler, ProvidesConverter readConverter) {
        return createProxy(HomeService.class, errorHandler, readConverter);
    }

    @Bean
    public EmployeeService getEmployeeRestClient(ResponseErrorHandler errorHandler, ProvidesConverter readConverter) {
        return createProxy(EmployeeService.class, errorHandler, readConverter);
    }

    @Bean
    public MissionSeriesService getMissionSeriesRestClient(ResponseErrorHandler errorHandler,
                                                           ProvidesConverter readConverter) {
        return createProxy(MissionSeriesService.class, errorHandler, readConverter);
    }

    @Bean
    public MissionService getMissionRestClient(ResponseErrorHandler errorHandler, ProvidesConverter readConverter) {
        return createProxy(MissionService.class, errorHandler, readConverter);
    }

    @Bean
    public PatientService getPatientRestClient(ResponseErrorHandler errorHandler, ProvidesConverter readConverter) {
        return createProxy(PatientService.class, errorHandler, readConverter);
    }

    private <T> T createProxy(Class<T> service, ResponseErrorHandler errorHandler, ProvidesConverter readConverter) {
        String host = "http://" + serverAddress + ":" + serverPort;
        return (T) new RestResourceProxy(service, host, readConverter, errorHandler)
                .getResourceProxy();
    }
}
