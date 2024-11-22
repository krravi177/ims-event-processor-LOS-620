package com.xpanse.ims.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * RestApiService which invokes API using RestTemplate.
 */
@Component
public class RestApiService {

    /**
     * Autowiring RestTemplate.
     **/
    private final RestTemplate restTemplate;

    public RestApiService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> postApiDataWithHeaders(final String url, final Object request,
                                                        final HttpHeaders headers, final Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
    }
}
