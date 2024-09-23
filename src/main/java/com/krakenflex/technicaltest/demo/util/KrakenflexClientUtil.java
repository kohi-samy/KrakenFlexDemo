package com.krakenflex.technicaltest.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.krakenflex.technicaltest.demo.constant.DemoConstants.X_API_KEY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class KrakenflexClientUtil {

    public static HttpEntity<String> getHttpEntity(String api_key){
        HttpHeaders headers = new HttpHeaders();
        /* headers.setContentType(APPLICATION_JSON); */
        List<MediaType> me = new ArrayList<MediaType>();
        me.add(APPLICATION_JSON);
        headers.setAccept(me);
        headers.set(X_API_KEY, api_key);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return httpEntity;
    }

    public static HttpEntity<String> getHttpEntity(String api_key, JsonNode siteOutages){
        HttpHeaders headers = new HttpHeaders();
        /* headers.setContentType(APPLICATION_JSON); */
        List<MediaType> me = new ArrayList<MediaType>();
        me.add(APPLICATION_JSON);
        headers.setAccept(me);
        headers.set(X_API_KEY, api_key);
        String requestBody = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            requestBody = objectMapper.writeValueAsString(siteOutages);
            HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
            return httpEntity;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
