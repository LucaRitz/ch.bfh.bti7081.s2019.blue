package ch.bfh.bti7081.s2019.blue;

import ch.bfh.bti7081.s2019.blue.client.rest.ProvidesConverter;
import ch.bfh.bti7081.s2019.blue.client.rest.RestResourceProxy;
import ch.bfh.bti7081.s2019.blue.client.ws.*;
import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstantsProvider;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    @Bean
    public FilterRegistrationBean loggingFilterRegistration(CommonsRequestLoggingFilter loggingFilter) {
        FilterRegistrationBean<CommonsRequestLoggingFilter> registration = new FilterRegistrationBean<>(loggingFilter);
        registration.addUrlPatterns("/rest/*");
        return registration;
    }

    // Rest Services
    @Bean
    public EmployeeService getEmployeeRestClient(ResponseErrorHandler errorHandler, ProvidesConverter converter) {
        return createProxy(EmployeeService.class, errorHandler, converter);
    }

    @Bean
    public MissionSeriesService getMissionSeriesRestClient(ResponseErrorHandler errorHandler,
                                                           ProvidesConverter converter) {
        return createProxy(MissionSeriesService.class, errorHandler, converter);
    }

    @Bean
    public MissionService getMissionRestClient(ResponseErrorHandler errorHandler, ProvidesConverter converter) {
        return createProxy(MissionService.class, errorHandler, converter);
    }

    @Bean
    public PatientService getPatientRestClient(ResponseErrorHandler errorHandler, ProvidesConverter converter) {
        return createProxy(PatientService.class, errorHandler, converter);
    }

    @Bean
    public ReportService getReportRestClient(ResponseErrorHandler errorHandler, ProvidesConverter converter) {
        return createProxy(ReportService.class, errorHandler, converter);
    }

    private <T> T createProxy(Class<T> service, ResponseErrorHandler errorHandler, ProvidesConverter converter) {
        String host = "http://" + serverAddress + ":" + serverPort;
        return new RestResourceProxy<>(service, host, converter, errorHandler).getResourceProxy();
    }
}
