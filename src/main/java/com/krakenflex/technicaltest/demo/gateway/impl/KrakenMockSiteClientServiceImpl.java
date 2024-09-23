package com.krakenflex.technicaltest.demo.gateway.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakenflex.technicaltest.demo.dto.Site;
import com.krakenflex.technicaltest.demo.exception.AuthorizationException;
import com.krakenflex.technicaltest.demo.exception.OutageServiceException;
import com.krakenflex.technicaltest.demo.exception.RequestLimitException;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.krakenflex.technicaltest.demo.constant.DemoConstants.SITE_INFO_PATH;
import static com.krakenflex.technicaltest.demo.util.KrakenflexClientUtil.getHttpEntity;

@Slf4j
@Service
public class KrakenMockSiteClientServiceImpl implements KrakenMockSiteClientService {

    @Value("${krakenflex.mock_apis_url}")
    private String krakenflex_mock_apis_url;

    @Value("${krakenflex.api-key}")
    private String api_key;
    private RestTemplate restTemplate;

    public KrakenMockSiteClientServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Site fetchSiteInfo(String site) {

        String fetchSiteInfoUrl = krakenflex_mock_apis_url + SITE_INFO_PATH + site;
        HttpEntity<String> httpEntity = getHttpEntity(api_key);
        Site siteInfo = null;
        try {
            ResponseEntity<String> siteInfoString = restTemplate.exchange(fetchSiteInfoUrl, HttpMethod.GET, httpEntity, String.class);

            if(siteInfoString.getStatusCode().is2xxSuccessful()){
                siteInfo = parseSiteInfo(siteInfoString.getBody());
            }

        }catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 403) {
                log.warn("Un Authorized access");
                throw new AuthorizationException("Un Authorized access");
            }
            if(e.getStatusCode().value() == 404){
                log.warn("Site info not available for site : {}", site);
                throw new OutageServiceException(String.format("Site info not available for site %s", site));
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
        return siteInfo;
    }

    private Site parseSiteInfo(String siteString){
        ObjectMapper objectMapper = new ObjectMapper();
        Site site = null;

        try{
            site = objectMapper.readValue(siteString,Site.class);
        }catch(JsonProcessingException e){
            log.error("Error while parsing to object {}", siteString);
        }
        return site;
    }
}
