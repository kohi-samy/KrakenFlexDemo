package com.krakenflex.technicaltest.demo.gateway;

import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.gateway.impl.KrakenMockOutageClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static com.krakenflex.technicaltest.demo.constant.DemoConstants.OUTAGE_PATH;
import static com.krakenflex.technicaltest.demo.util.KrakenflexClientUtil.getHttpEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KrakenMockOutageClientServiceTest {

    @InjectMocks
    KrakenMockOutageClientServiceImpl krakenMockOutageClientServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void retrieveAllOutagesSuccessfully(){

        ReflectionTestUtils.setField(krakenMockOutageClientServiceImpl,"krakenflex_mock_apis_url", "https://api.krakenflex.systems/interview-tests-mock-api/v1");
        ReflectionTestUtils.setField(krakenMockOutageClientServiceImpl,"api_key", "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23");

        String fetchOutagesUrl = "https://api.krakenflex.systems/interview-tests-mock-api/v1" + OUTAGE_PATH ;
        String api_key = "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23";
        when(restTemplate.exchange(fetchOutagesUrl, HttpMethod.GET, getHttpEntity(api_key), String.class)).thenReturn(ResponseEntity.ok(outageResponseString()));
        OutageResponse response= krakenMockOutageClientServiceImpl.retrieveAllOutages();

        assertEquals("0e4d59ba-43c7-4451-a8ac-ca628bcde417", response.getOutageInfoList().get(0).getId());

    }

    @Test
    void retrieveAllOutages_Exception(){

        ReflectionTestUtils.setField(krakenMockOutageClientServiceImpl,"krakenflex_mock_apis_url", "https://api.krakenflex.systems/interview-tests-mock-api/v1");
        ReflectionTestUtils.setField(krakenMockOutageClientServiceImpl,"api_key", "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23");

        String fetchOutagesUrl = "https://api.krakenflex.systems/interview-tests-mock-api/v1" + OUTAGE_PATH ;
        String api_key = "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23";
        when(restTemplate.exchange(fetchOutagesUrl, HttpMethod.GET, getHttpEntity(api_key), String.class)).thenThrow(new RuntimeException("exception"));
        try {
            OutageResponse response = krakenMockOutageClientServiceImpl.retrieveAllOutages();
        }catch(Exception e){
            assertEquals("exception", e.getMessage());
        }

    }



    private String outageResponseString(){

        return "[" +
                "    {" +
                "        \"id\": \"0e4d59ba-43c7-4451-a8ac-ca628bcde417\"," +
                "        \"begin\": \"2022-02-15T11:28:26.735Z\"," +
                "        \"end\": \"2022-08-28T03:37:48.568Z\"" +
                "    }" +
                "]";
    }

}
