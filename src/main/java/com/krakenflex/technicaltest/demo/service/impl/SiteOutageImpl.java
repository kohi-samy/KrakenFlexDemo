package com.krakenflex.technicaltest.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakenflex.technicaltest.demo.dto.*;
import com.krakenflex.technicaltest.demo.exception.OutageServiceException;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockOutageClientService;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteClientService;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteOutageClientService;
import com.krakenflex.technicaltest.demo.service.SiteOutageService;
import com.krakenflex.technicaltest.demo.util.KrakenflexDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SiteOutageImpl implements SiteOutageService {

    @Value("${demo.cutOffDate}")
    private String cutOffDate;

    private KrakenMockSiteOutageClientService krakenMockSiteOutageClientService;
    private KrakenMockSiteClientService krakenMockSiteClientService;
    private KrakenMockOutageClientService krakenMockOutageClientService;

    public SiteOutageImpl(KrakenMockSiteOutageClientService krakenMockSiteOutageClientService, KrakenMockSiteClientService krakenMockSiteClientService, KrakenMockOutageClientService krakenMockOutageClientService){
        this.krakenMockSiteOutageClientService = krakenMockSiteOutageClientService;
        this.krakenMockSiteClientService = krakenMockSiteClientService;
        this.krakenMockOutageClientService = krakenMockOutageClientService;
    }

    @Override
    public Object filterOutagesOfSiteAndPost(String site) {

        Site siteInfo = krakenMockSiteClientService.fetchSiteInfo(site);

        if(Objects.isNull(siteInfo)){
            log.error("No site info available for site : {}", site);
            throw new OutageServiceException(String.format("Site info not available : %s", site));
        }

        log.info("Site info available for site : {}", site);
        log.debug("Site info available for site : {} info : {}", site, siteInfo);

        Map<String, String> deviceMap = siteInfo.getDevices().stream().collect(Collectors.toMap(Devices::getId,Devices::getName));

        if(deviceMap.isEmpty()){
            log.error("Device not available in this site: : {}", site);
            throw new OutageServiceException(String.format("Device not available in this site: %s", site));
        }

        log.info("Available device list in site : {} are {}", site, deviceMap.keySet().toString());

        OutageResponse outages = krakenMockOutageClientService.retrieveAllOutages();

        if(Objects.isNull(outages)){
            log.error("Outages not happened");
            throw new OutageServiceException("Outages not happened");
        }

        log.info("Outages registered in total: {}", outages.getOutageInfoList().size());
        log.debug("Outages registered in total: {} are {}", outages.getOutageInfoList().size(), outages.getOutageInfoList().toString());

        ZonedDateTime zdt = KrakenflexDateUtil.parseZoneDate(cutOffDate);

        List<String> outageJsonList = new ArrayList<>();

        outages.getOutageInfoList().stream()
                .filter(o -> !(o.getBegin().isBefore(zdt)) && deviceMap.containsKey(o.getId()))
                .forEach(e -> mapToJson(e, outageJsonList, deviceMap));

        if(outageJsonList.isEmpty()){
            log.error("No outages in the this site : {}", site);
            throw new OutageServiceException(String.format("No outages in the this site: %s", site));
        }

        log.info("Outages in the site: {} count : {}", site, outageJsonList.size());
        log.debug("Outages registered in total: {} are {}", outageJsonList.size(), outageJsonList.toString());

        JsonNode jsonNode = getJsonNode(outageJsonList);

        return krakenMockSiteOutageClientService.createSiteOutage(jsonNode, site);
    }

    private void mapToJson(OutageInfo outageInfo, List<String> outageList, Map<String, String> deviceMap){

        ObjectMapper objectMapper = new ObjectMapper();

        EnhancedOutageInfo enhancedOutageInfo = new EnhancedOutageInfo();
        enhancedOutageInfo.setId(outageInfo.getId());
        enhancedOutageInfo.setName(deviceMap.get(outageInfo.getId()));
        enhancedOutageInfo.setBegin(KrakenflexDateUtil.formatDate(outageInfo.getBegin()));
        enhancedOutageInfo.setEnd(KrakenflexDateUtil.formatDate(outageInfo.getEnd()));

        try {
            String objectString = objectMapper.writeValueAsString(enhancedOutageInfo);
            outageList.add(objectString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode getJsonNode(List<String> outageJsonList) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(outageJsonList.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }

   /* @Override
    public String createSiteOutage(SiteOutages siteOutages, String site) {

        List<String> outageList = new ArrayList<>();

        for (EnhancedOutageInfo e : siteOutages.getSiteOutages()) {
            mapToJson(e, outageList);
        }

        JsonNode jsonNode = getJsonNode(outageList);

        return krakenMockSiteOutageClientService.createSiteOutage(jsonNode, site);
    }*/

    private void mapToJson(EnhancedOutageInfo enhancedOutageInfo, List<String> outageList){

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String objectString = objectMapper.writeValueAsString(enhancedOutageInfo);
            outageList.add(objectString);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
