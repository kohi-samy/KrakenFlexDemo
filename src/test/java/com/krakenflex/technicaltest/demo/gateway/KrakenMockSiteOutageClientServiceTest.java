package com.krakenflex.technicaltest.demo.gateway;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakenflex.technicaltest.demo.dto.EnhancedOutageInfo;

import com.krakenflex.technicaltest.demo.gateway.impl.KrakenMockSiteOutageClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.krakenflex.technicaltest.demo.constant.DemoConstants.SITE_OUTAGE_PATH;
import static com.krakenflex.technicaltest.demo.util.KrakenflexClientUtil.getHttpEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KrakenMockSiteOutageClientServiceTest {

    @InjectMocks
    KrakenMockSiteOutageClientServiceImpl krakenMockSiteOutageClientService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void retrieveAllOutagesSuccessfully(){

        ReflectionTestUtils.setField(krakenMockSiteOutageClientService,"krakenflex_mock_apis_url", "https://api.krakenflex.systems/interview-tests-mock-api/v1");
        ReflectionTestUtils.setField(krakenMockSiteOutageClientService,"api_key", "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23");

        String postSiteOutageUrl = "https://api.krakenflex.systems/interview-tests-mock-api/v1" + SITE_OUTAGE_PATH + "testSite" ;
        String api_key = "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23";
        Object obj = new Object();
        when(restTemplate.exchange(postSiteOutageUrl, HttpMethod.POST, getHttpEntity(api_key, getJsonNode()), Object.class)).thenReturn(ResponseEntity.ok(obj));
        Object response= krakenMockSiteOutageClientService.createSiteOutage(getJsonNode(), "testSite");

        assertEquals(obj,response);


    }

    private static JsonNode getJsonNode() {

        List<String> outageList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        EnhancedOutageInfo enhancedOutageInfo = new EnhancedOutageInfo();
        enhancedOutageInfo.setId("test");
        enhancedOutageInfo.setName("test");
        enhancedOutageInfo.setBegin("test");
        enhancedOutageInfo.setEnd("test");

        JsonNode jsonNode = null;
        try {

            String objectString = objectMapper.writeValueAsString(enhancedOutageInfo);
            outageList.add(objectString);

            jsonNode = objectMapper.readTree(outageList.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }
}
