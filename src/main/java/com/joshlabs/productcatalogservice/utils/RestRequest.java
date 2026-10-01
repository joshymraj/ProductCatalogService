package com.joshlabs.productcatalogservice.utils;

import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestRequest<T> {

    private final RestTemplate restTemplate;
    private final String url;
    private final HttpMethod httpMethod;
    private final Object requestBody;
    private final String bearerToken;
    private final MediaType mediaType;
    private final Class<T> responseType;
    private final ParameterizedTypeReference<T> parameterizedTypeReference;
    private final Object[] uriVariables;

    private RestRequest(Builder<T> builder) {
        this.restTemplate = builder.getRestTemplate();
        this.url = builder.getUrl();
        this.httpMethod = builder.getHttpMethod();
        this.requestBody = builder.getRequestBody();
        this.bearerToken = builder.getBearerToken();
        this.mediaType = builder.getMediaType();
        this.responseType = builder.getResponseType();
        this.parameterizedTypeReference = builder.getParameterizedTypeReference();
        this.uriVariables = builder.getUriVariables();
    }

    public static <T> Builder<T> builder(RestTemplate restTemplate) {
        return new Builder<>(restTemplate);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (requestBody != null) {
            httpHeaders.setContentType(this.mediaType);
        }
        if (bearerToken != null) {
            httpHeaders.setBearerAuth(this.bearerToken);
        }
        return httpHeaders;
    }

    public ResponseEntity<T> execute() {

        HttpEntity<?> httpEntity = new HttpEntity<>(this.requestBody, buildHeaders());
        if (parameterizedTypeReference == null) {
            return this.restTemplate.exchange(this.url, this.httpMethod, httpEntity, responseType, uriVariables);
        }
        return this.restTemplate.exchange(this.url, this.httpMethod, httpEntity, parameterizedTypeReference, uriVariables);
    }

    @Getter
    public static class Builder<T> {

        private final RestTemplate restTemplate;
        private String url;
        private HttpMethod httpMethod;
        private Object requestBody = null;
        private String bearerToken;
        private MediaType mediaType = MediaType.APPLICATION_JSON;
        private Class<T> responseType;
        private ParameterizedTypeReference<T> parameterizedTypeReference;
        private Object[] uriVariables;

        private Builder(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public Builder<T> setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder<T> setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder<T> setRequestBody(Object requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder<T> setBearerToken(String bearerToken) {
            this.bearerToken = bearerToken;
            return this;
        }

        public Builder<T> setMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public Builder<T> setResponseType(Class<T> responseType) {
            if (this.parameterizedTypeReference != null) {
                throw new IllegalStateException("Parameterized response type already set. You cannot set both simple and parameterized response types");
            }
            this.responseType = responseType;
            return this;
        }

        public Builder<T> setResponseType(ParameterizedTypeReference<T> responseType) {
            if (this.responseType != null) {
                throw new IllegalStateException("Simple response type already set. You cannot set both simple and parameterized response types");
            }
            this.parameterizedTypeReference = responseType;
            return this;
        }

        public Builder<T> setUriVariables(Object... uriVariables) {
            this.uriVariables = uriVariables;
            return this;
        }

        public RestRequest<T> build() {
            if (restTemplate == null) {
                throw new IllegalArgumentException("RestTemplate cannot be null.");
            }

            if (httpMethod == null) {
                throw new IllegalArgumentException("HTTP method cannot be null.");
            }

            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("URL cannot be null or blank.");
            }

            if (responseType == null && parameterizedTypeReference == null) {
                throw new IllegalArgumentException("No response type set.");
            }

            return new RestRequest<>(this);
        }
    }
}
