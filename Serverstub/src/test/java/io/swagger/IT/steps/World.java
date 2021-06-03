package io.swagger.IT.steps;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class World {
    private HttpHeaders headers = new HttpHeaders();
    private int lastResponseCode;
    private ResponseEntity<?> lastResponse;
    private RestTemplate restTemplate = new RestTemplate();

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }

    public void setLastResponseCode(int lastResponseCode) {
        this.lastResponseCode = lastResponseCode;
    }

    public ResponseEntity<?> getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(ResponseEntity<?> lastResponse) {
        this.lastResponse = lastResponse;
    }

    public void matchLastResponse(int code) throws Exception {
        if (lastResponseCode != code)
            throw new Exception(String.format("Http code %d does not match expected %d", lastResponseCode, code));
    }

    public <T> ResponseEntity<T> getRequest(String uri, Class<T> target) throws Exception {
        return getRequest(new URI(uri), target);
    }

    public <T> ResponseEntity<T> getRequest(URI uri, Class<T> target) throws Exception {
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        return exchangeRequest(uri, target, entity, HttpMethod.GET);
    }

    public <T> ResponseEntity<T> postRequest(String uri, Class<T> target, Object o) throws Exception {
        return postRequest(new URI(uri), target, o);
    }

    public <T> ResponseEntity<T> postRequest(URI uri, Class<T> target, Object o) throws Exception {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(o, headers);
        return exchangeRequest(uri, target, entity, HttpMethod.POST);
    }

    public <T> ResponseEntity<T> putRequest(String uri, Class<T> target, Object o) throws Exception {
        return putRequest(new URI(uri), target, o);
    }

    public <T> ResponseEntity<T> putRequest(URI uri, Class<T> target, Object o) throws Exception {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(o, headers);
        return exchangeRequest(uri, target, entity, HttpMethod.PUT);
    }

    public <T> ResponseEntity<T> deleteRequest(String uri, Class<T> target, Object o) throws Exception {
        return deleteRequest(new URI(uri), target, o);
    }

    public <T> ResponseEntity<T> deleteRequest(URI uri, Class<T> target, Object o) throws Exception {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(o, headers);
        return exchangeRequest(uri, target, entity, HttpMethod.DELETE);
    }

    private <T> ResponseEntity<T> exchangeRequest(URI uri, Class<T> target, HttpEntity<Object> entity, HttpMethod method){
        try {
            ResponseEntity<T> response = restTemplate.exchange(uri, method, entity, target);
            lastResponse = response;
            lastResponseCode = response.getStatusCodeValue();
            return response;
        }
        catch (HttpClientErrorException e) {
            lastResponse = null;
            lastResponseCode = e.getRawStatusCode();
            return null;
        }
    }
}
