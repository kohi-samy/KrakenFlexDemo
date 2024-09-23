package com.krakenflex.technicaltest.demo.gateway.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.krakenflex.technicaltest.demo.exception.AuthorizationException;
import com.krakenflex.technicaltest.demo.exception.OutageServiceException;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteOutageClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.krakenflex.technicaltest.demo.constant.DemoConstants.SITE_OUTAGE_PATH;
import static com.krakenflex.technicaltest.demo.util.KrakenflexClientUtil.getHttpEntity;

@Slf4j
@Service
public class KrakenMockSiteOutageClientServiceImpl implements KrakenMockSiteOutageClientService {

    @Value("${krakenflex.mock_apis_url}")
    private String krakenflex_mock_apis_url;

    @Value("${krakenflex.api-key}")
    private String api_key;
    private RestTemplate restTemplate;

    public KrakenMockSiteOutageClientServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public String createSiteOutage(JsonNode siteOutages, String site) {

        String createSiteOutageUrl = krakenflex_mock_apis_url + SITE_OUTAGE_PATH + site;
        HttpEntity<String> httpEntity = getHttpEntity(api_key, siteOutages);

        ResponseEntity<String> response = null;

        try {
            response = restTemplate.exchange(createSiteOutageUrl, HttpMethod.POST, httpEntity, String.class);
        }catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 400)
            {
                log.warn("Wrong status configured in server");
                return String.format("Outages Submitted for the site %s" , site);
            }
            if(e.getStatusCode().value() == 403) {
                log.warn("Un Authorized access");
                throw new AuthorizationException("Un Authorized access");
            }
            if(e.getStatusCode().value() == 404){
                log.warn("Site info not available for site : {}", site);
                throw new OutageServiceException(String.format("Site info not available for site %s", site));
            }
            if(e.getStatusCode().is5xxServerError())
            {
                log.warn("Internal server error");
                throw new RuntimeException("Internal server error, please try again later. ");
            }
        }

        return Objects.nonNull(response) ? response.getBody() : "";

    }

}
