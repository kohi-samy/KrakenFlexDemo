package com.krakenflex.technicaltest.demo.gateway;


import com.krakenflex.technicaltest.demo.dto.Site;
import com.krakenflex.technicaltest.demo.gateway.impl.KrakenMockSiteClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static com.krakenflex.technicaltest.demo.constant.DemoConstants.SITE_INFO_PATH;
import static com.krakenflex.technicaltest.demo.util.KrakenflexClientUtil.getHttpEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KrakenMockSiteClientServiceTest {

    @InjectMocks
    KrakenMockSiteClientServiceImpl krakenMockSiteClientService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void retrieveAllOutagesSuccessfully(){

        ReflectionTestUtils.setField(krakenMockSiteClientService,"krakenflex_mock_apis_url", "https://api.krakenflex.systems/interview-tests-mock-api/v1");
        ReflectionTestUtils.setField(krakenMockSiteClientService,"api_key", "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23");

        String fetchSiteDetailUrl = "https://api.krakenflex.systems/interview-tests-mock-api/v1" + SITE_INFO_PATH + "testSite" ;
        String api_key = "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23";
        when(restTemplate.exchange(fetchSiteDetailUrl, HttpMethod.GET, getHttpEntity(api_key), String.class)).thenReturn(ResponseEntity.ok(siteResponseString()));
        Site response= krakenMockSiteClientService.fetchSiteInfo("testSite");

        assertEquals("111183e7-fb90-436b-9951-63392b36bdd2", response.getDevices().get(0).getId());

    }

    @Test
    void retrieveAllOutages_ThrowException(){

        ReflectionTestUtils.setField(krakenMockSiteClientService,"krakenflex_mock_apis_url", "https://api.krakenflex.systems/interview-tests-mock-api/v1");
        ReflectionTestUtils.setField(krakenMockSiteClientService,"api_key", "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23");

        String fetchSiteDetailUrl = "https://api.krakenflex.systems/interview-tests-mock-api/v1" + SITE_INFO_PATH + "testSite" ;
        String api_key = "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23";
        when(restTemplate.exchange(fetchSiteDetailUrl, HttpMethod.GET, getHttpEntity(api_key), String.class)).thenThrow(new RuntimeException("Exception"));
        try {
            Site Exception = krakenMockSiteClientService.fetchSiteInfo("testSite");
        }catch(Exception e){
            assertEquals("Exception", e.getMessage());
        }

    }

    private String siteResponseString(){

        return "{" +
                "    \"id\": \"test-Site\"," +
                "    \"name\": \"testSite\"," +
                "    \"devices\": [" +
                "        {" +
                "            \"id\": \"111183e7-fb90-436b-9951-63392b36bdd2\"," +
                "            \"name\": \"Battery 1\"" +
                "        }," +
                "        {" +
                "            \"id\": \"86b5c819-6a6c-4978-8c51-a2d810bb9318\"," +
                "            \"name\": \"Battery 2\"" +
                "        }]}";
    }
}
