package ch.bfh.bti7081.s2019.blue.client.ws;

import com.vaadin.flow.component.notification.Notification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestErrorHandler implements ResponseErrorHandler {
    private static final String ERROR_KEY = "errors";

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        if (headers.containsKey(ERROR_KEY)) {
            headers.get(ERROR_KEY).forEach(Notification::show);
        }
    }
}
