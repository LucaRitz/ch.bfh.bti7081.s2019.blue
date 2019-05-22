package ch.bfh.bti7081.s2019.blue.client.rest;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class RestResourceProxy implements InvocationHandler {

    private final ProvidesConverter converter;
    private final Map<String, Configuration> configMapping;
    private final Map<String, RestResourceProxy> subProxies;
    private final String parentResourcePath;
    private final RestTemplate template;
    private final Object resourceProxy;

    private Object[] parentParams;

    public RestResourceProxy(Class<?> resource, String host, ProvidesConverter converter,
                             ResponseErrorHandler errorHandler) {
        this(resource, converter, errorHandler, host);
    }

    private RestResourceProxy(Class<?> resource, ProvidesConverter converter, ResponseErrorHandler errorHandler,
                              String parentResourcePath) {
        this.converter = converter;
        this.configMapping = new HashMap<>();
        this.subProxies = new HashMap<>();
        this.parentResourcePath = parentResourcePath;
        this.template = new RestTemplate();
        this.template.setErrorHandler(errorHandler);

        this.resourceProxy = Proxy.newProxyInstance(RestResourceProxy.class.getClassLoader(), new Class<?>[]{resource}, this);
        initialize(resource);
    }

    public Object getResourceProxy() {
        return resourceProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if ("hashCode".equals(method.getName())) {
            return hashCode();
        }

        String methodName = method.getName();
        Object[] allParams = ArrayUtils.addAll((parentParams != null ? parentParams : new Object[0]),
                (args != null ? args : new Object[0]));

        if (subProxies.containsKey(methodName)) {
            RestResourceProxy subProxy = subProxies.get(methodName);
            subProxy.parentParams = allParams;
            return subProxy.getResourceProxy();
        }

        return doRequest(method, allParams);
    }

    private Object doRequest(Method method, Object[] args) {
        Configuration configuration = configMapping.get(method.getName());
        CompletableFuture<Object> future = new CompletableFuture<>();

        Object[] convertedParams = Arrays.stream(args)
                .map(converter::convertParam)
                .toArray();
        Class<?> requestBodyType = findBodyType(method);
        Object requestBody = findRequestBody(requestBodyType, convertedParams);
        if (requestBody != null) {
            convertedParams = remove(requestBody, convertedParams);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<?> response;
        if (configuration.typeReference != null) {
            response = template.exchange(configuration.resourcePath, configuration.method, requestEntity,
                    configuration.typeReference, convertedParams);
        } else {
            response = template.exchange(configuration.resourcePath, configuration.method, requestEntity,
                    configuration.returnType, convertedParams);
        }

        if (hasErrors(response)) {
            future.completeExceptionally(new RequestException());
        } else {

            future.complete(response.getBody());
        }

        return future;
    }

    private void initialize(Class<?> resource) {
        String fullResourcePath = getFullResourcePath(resource);

        for (Method method : resource.getDeclaredMethods()) {
            String methodResourcePath = fullResourcePath +
                    (method.isAnnotationPresent(Path.class) ? method.getAnnotation(Path.class).value() : "");
            if (IsRestService.class.isAssignableFrom(method.getReturnType())) {
                initializeSubProxy(methodResourcePath, method);
                continue;
            }
            initializeConfigForResourceMethod(methodResourcePath, method);
        }
    }

    private String getFullResourcePath(Class<?> resource) {
        return (parentResourcePath != null ? parentResourcePath : "") +
                (resource.isAnnotationPresent(Path.class) ? resource.getAnnotation(Path.class).value() : "");
    }

    private void initializeConfigForResourceMethod(String fullResourcePath, Method method) {
        String resourcePath = fullResourcePath + readQueryParams(method);
        configMapping.put(method.getName(), new Configuration(resourcePath, readReturnType(method),
                readHttpMethod(method), readTypeReference(method)));
    }

    private void initializeSubProxy(String fullResourcePath, Method method) {
        RestResourceProxy subProxy = new RestResourceProxy(method.getReturnType(), converter, template.getErrorHandler(),
                fullResourcePath);
        subProxies.put(method.getName(), subProxy);
    }

    private ParameterizedTypeReference<?> readTypeReference(Method method) {
        if (method.isAnnotationPresent(ReturnType.class)) {
            return converter.getTypeReference(method.getAnnotation(ReturnType.class).value());
        }
        return null;
    }

    private HttpMethod readHttpMethod(Method method) {
        HttpMethod httpMethod;
        if (method.isAnnotationPresent(GetMapping.class)) {
            httpMethod = HttpMethod.GET;
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            httpMethod = HttpMethod.POST;
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            httpMethod = HttpMethod.PUT;
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            httpMethod = HttpMethod.DELETE;
        } else {
            throw new IllegalArgumentException("Mapping not implemented: " + method.getName());
        }
        return httpMethod;
    }

    private Class<?> readReturnType(Method method) {
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            Type[] subTypes = ((ParameterizedType) type).getActualTypeArguments();
            if (subTypes.length > 0) {
                Type subType = subTypes[0];
                return TypeFactory.rawClass(subType);
            }
        }
        return null;
    }

    private boolean hasErrors(ResponseEntity<?> entity) {
        List<String> errors = entity.getHeaders().getOrDefault("errors", Collections.emptyList());
        return !errors.isEmpty();
    }

    private Object[] remove(Object toRemove, Object[] array) {
        Object[] newArray = new Object[array.length - 1];
        int index = 0;

        for (Object object : array) {
            if (!Objects.equals(toRemove, object)) {
                newArray[index] = object;
                index++;
            }
        }

        return newArray;
    }

    private Object findRequestBody(Class<?> requestBodyType, Object[] params) {
        if (requestBodyType == null) {
            return null;
        }

        return Arrays.stream(params)
                .filter(requestBodyType::isInstance)
                .findFirst()
                .orElse(null);
    }

    private Class<?> findBodyType(Method method) {
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                return parameter.getType();
            }
        }
        return null;
    }

    private String readQueryParams(Method method) {
        List<String> queryParams = new ArrayList<>();
        for (Annotation[] annotations : method.getParameterAnnotations()) {
            Arrays.stream(annotations)
                    .filter(annotation -> annotation instanceof RequestParam)
                    .map(annotation -> (RequestParam) annotation)
                    .map(param -> param.value() + "={" + param.value() + "}")
                    .findFirst()
                    .ifPresent(queryParams::add);
        }

        return queryParams.isEmpty() ? "" : "?" + String.join("&", queryParams);
    }

    private static class Configuration {
        private final String resourcePath;
        private final Class<?> returnType;
        private final HttpMethod method;
        private final ParameterizedTypeReference<?> typeReference;

        Configuration(String resourcePath, Class<?> returnType, HttpMethod method,
                      ParameterizedTypeReference<?> typeReference) {
            this.resourcePath = resourcePath;
            this.returnType = returnType;
            this.method = method;
            this.typeReference = typeReference;
        }
    }
}
