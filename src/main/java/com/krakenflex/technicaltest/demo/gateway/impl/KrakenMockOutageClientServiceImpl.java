package com.krakenflex.technicaltest.demo.gateway.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakenflex.technicaltest.demo.dto.OutageInfo;
import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.exception.AuthorizationException;
import com.krakenflex.technicaltest.demo.exception.OutageServiceException;
import com.krakenflex.technicaltest.demo.exception.RequestLimitException;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockOutageClientService;
import com.krakenflex.technicaltest.demo.util.KrakenflexDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.krakenflex.technicaltest.demo.constant.DemoConstants.OUTAGE_PATH;
import static com.krakenflex.technicaltest.demo.util.KrakenflexClientUtil.getHttpEntity;

@Service
@Slf4j
public class KrakenMockOutageClientServiceImpl implements KrakenMockOutageClientService {

    @Value("${krakenflex.mock_apis_url}")
    private String krakenflex_mock_apis_url;

    @Value("${krakenflex.api-key}")
    private String api_key;

    private RestTemplate restTemplate;

    public KrakenMockOutageClientServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public OutageResponse retrieveAllOutages() {

        String fetchOutagesUrl = krakenflex_mock_apis_url + OUTAGE_PATH ;
        HttpEntity<String> httpEntity = getHttpEntity(api_key);
        OutageResponse outageResponse = new OutageResponse();

        try {
            ResponseEntity<String> outages = restTemplate.exchange(fetchOutagesUrl, HttpMethod.GET, httpEntity, String.class);

            if(outages.getStatusCode().is2xxSuccessful()){
                parseOutageResponse(outages.getBody(), outageResponse);
            }

        }catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 403) {
                log.warn("Un Authorized access");
                throw new AuthorizationException("Un Authorized access");
            }
            if(e.getStatusCode().value() == 429){
                log.warn("Exceed request count");
                throw new RequestLimitException("Too many request made, Exceed the limit.");
            }
            if(e.getStatusCode().is5xxServerError())
            {
                log.warn("Internal server error");
                throw new RuntimeException("Internal server error, please try again later. ");
            }
        }
       return outageResponse;
    }
    
    private static void parseOutageResponse(String response, OutageResponse outageResponse){
        ObjectMapper objectMapper = new ObjectMapper();

        List<OutageInfo> outageInfoList= new ArrayList<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);

            IntStream.range(0, jsonNode.size())
                    .mapToObj(jsonNode::get)
                    .forEach(node -> mapToOutageInfo(node, outageInfoList));

            outageResponse.setOutageInfoList(outageInfoList);

        }catch (JsonProcessingException e){
            log.error("Error while parsing to object {}", response);
        }
    }

    private static void mapToOutageInfo(JsonNode data, List<OutageInfo> outageInfoList) {
        OutageInfo outageInfo = new OutageInfo();
        outageInfo.setId(data.get("id").asText());
        outageInfo.setBegin(KrakenflexDateUtil.parseZoneDate(data.get("begin").asText()));
        outageInfo.setEnd(KrakenflexDateUtil.parseZoneDate(data.get("end").asText()));
        outageInfoList.add(outageInfo);
    }
}
