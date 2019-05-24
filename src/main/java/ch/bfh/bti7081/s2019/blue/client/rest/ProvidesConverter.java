package ch.bfh.bti7081.s2019.blue.client.rest;

import org.springframework.core.ParameterizedTypeReference;

public interface ProvidesConverter {
    ParameterizedTypeReference<?> getTypeReference(String key);

    Object convertParam(Object param);
}
