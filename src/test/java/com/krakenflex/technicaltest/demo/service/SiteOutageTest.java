package com.krakenflex.technicaltest.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakenflex.technicaltest.demo.dto.*;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockOutageClientService;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteClientService;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteOutageClientService;
import com.krakenflex.technicaltest.demo.service.impl.SiteOutageImpl;
import com.krakenflex.technicaltest.demo.util.KrakenflexDateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SiteOutageTest {
    @Mock
    private KrakenMockSiteOutageClientService krakenMockSiteOutageClientService;
    @Mock
    private KrakenMockSiteClientService krakenMockSiteClientService;
    @Mock
    private KrakenMockOutageClientService krakenMockOutageClientService;
    @InjectMocks
    SiteOutageImpl siteOutageImpl;

    @Test
    void filterOutagesOfSiteAndPostSuccessfully_withAllExpectedOutage(){

        ReflectionTestUtils.setField(siteOutageImpl,"cutOffDate", "2022-01-01T00:00:00.000Z");
        Object object = new Object();
        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(getSite());
        when(krakenMockOutageClientService.retrieveAllOutages()).thenReturn(getAllOutageList());

        List<String> outageJsonList = new ArrayList<>();
        getFilteredOutageList().getOutageInfoList().stream().forEach(e -> mapToJson(e, outageJsonList, getDeviceMap()));
        JsonNode jsonNode = getJsonNode(outageJsonList);

        when(krakenMockSiteOutageClientService.createSiteOutage(jsonNode, "test-Site")).thenReturn(object);

        Object response= siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");

        assertEquals(object, response);

    }

    @Test
    void filterOutagesOfSiteAndPost_Exception_siteInfoNull(){

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(null);
        try {
            Object response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
        }catch(Exception e){
            assertEquals("Site info not available : test-Site", e.getMessage());
        }

    }

    @Test
    void filterOutagesOfSiteAndPost_Exception_deviceEmpty(){

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(getSiteWithNoDevice());
        try {
            Object response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
        }catch(Exception e){
            assertEquals("Device not available in this site: test-Site", e.getMessage());
        }

    }

    @Test
    void filterOutagesOfSiteAndPost_Exception_OutagesNull(){

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(getSite());
        when(krakenMockOutageClientService.retrieveAllOutages()).thenReturn(null);

        try {
            Object response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
        }catch(Exception e){
            assertEquals("Outages not happened", e.getMessage());
        }
    }

    @Test
    void filterOutagesOfSiteAndPost_Exception_NoOutagesInSite(){

        ReflectionTestUtils.setField(siteOutageImpl,"cutOffDate", "2022-01-01T00:00:00.000Z");

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(getSite());
        when(krakenMockOutageClientService.retrieveAllOutages()).thenReturn(getOutageListNotForTheSite());

        try {
            Object response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
        }catch(Exception e){
            assertEquals("No outages in the this site: test-Site", e.getMessage());
        }
    }
    private Site getSite(){

        Site site = new Site();
        site.setId("test-Site");
        site.setName("testName");
        Devices device = new Devices();
        device.setId("111183e7-fb90-436b-9951-63392b36bdd2");
        device.setName("Battery 1");
        Devices device2 = new Devices();
        device2.setId("86b5c819-6a6c-4978-8c51-a2d810bb9318");
        device2.setName("Battery 2");
        List<Devices> devicesList = new ArrayList<>();
        devicesList.add(device);
        devicesList.add(device2);
        site.setDevices(devicesList);

        return site;
    }

    private Site getSiteWithNoDevice(){

        Site site = new Site();
        site.setId("test-Site");
        site.setName("testName");

        List<Devices> devicesList = new ArrayList<>();

        site.setDevices(devicesList);

        return site;
    }

    private OutageResponse getAllOutageList(){

        OutageResponse outageResponse = new OutageResponse();

        List<OutageInfo> outageInfoList = new ArrayList<>();
        OutageInfo outageInfo1 = new OutageInfo();
        outageInfo1.setId("0e4d59ba-43c7-4451-a8ac-ca628bcde417");
        outageInfo1.setBegin(KrakenflexDateUtil.parseZoneDate("2022-02-15T11:28:26.735Z"));
        outageInfo1.setEnd(KrakenflexDateUtil.parseZoneDate("2022-08-28T03:37:48.568Z"));

        OutageInfo outageInfo2 = new OutageInfo();
        outageInfo2.setId("111183e7-fb90-436b-9951-63392b36bdd2");
        outageInfo2.setBegin(KrakenflexDateUtil.parseZoneDate("2022-02-18T01:01:20.142Z"));
        outageInfo2.setEnd(KrakenflexDateUtil.parseZoneDate("2022-08-15T14:34:50.366Z"));

        OutageInfo outageInfo3 = new OutageInfo();
        outageInfo3.setId("86b5c819-6a6c-4978-8c51-a2d810bb9318");
        outageInfo3.setBegin(KrakenflexDateUtil.parseZoneDate("2022-02-16T07:01:50.149Z"));
        outageInfo3.setEnd(KrakenflexDateUtil.parseZoneDate("2022-10-03T07:46:31.41Z"));

        OutageInfo outageInfo4 = new OutageInfo();
        outageInfo4.setId("111183e7-fb90-436b-9951-63392b36bdd2");
        outageInfo4.setBegin(KrakenflexDateUtil.parseZoneDate("2022-01-01T00:00:00.000Z"));
        outageInfo4.setEnd(KrakenflexDateUtil.parseZoneDate("2022-09-15T19:45:10.341Z"));

        outageInfoList.add(outageInfo1);
        outageInfoList.add(outageInfo2);
        outageInfoList.add(outageInfo3);
        outageInfoList.add(outageInfo4);
        outageResponse.setOutageInfoList(outageInfoList);
        return outageResponse;

    }

    private OutageResponse getFilteredOutageList(){

        OutageResponse outageResponse = new OutageResponse();

        List<OutageInfo> outageInfoList = new ArrayList<>();

        OutageInfo outageInfo2 = new OutageInfo();
        outageInfo2.setId("111183e7-fb90-436b-9951-63392b36bdd2");
        outageInfo2.setBegin(KrakenflexDateUtil.parseZoneDate("2022-02-18T01:01:20.142Z"));
        outageInfo2.setEnd(KrakenflexDateUtil.parseZoneDate("2022-08-15T14:34:50.366Z"));

        OutageInfo outageInfo3 = new OutageInfo();
        outageInfo3.setId("86b5c819-6a6c-4978-8c51-a2d810bb9318");
        outageInfo3.setBegin(KrakenflexDateUtil.parseZoneDate("2022-02-16T07:01:50.149Z"));
        outageInfo3.setEnd(KrakenflexDateUtil.parseZoneDate("2022-10-03T07:46:31.41Z"));

        OutageInfo outageInfo4 = new OutageInfo();
        outageInfo4.setId("111183e7-fb90-436b-9951-63392b36bdd2");
        outageInfo4.setBegin(KrakenflexDateUtil.parseZoneDate("2022-01-01T00:00:00.000Z"));
        outageInfo4.setEnd(KrakenflexDateUtil.parseZoneDate("2022-09-15T19:45:10.341Z"));

        outageInfoList.add(outageInfo2);
        outageInfoList.add(outageInfo3);
        outageInfoList.add(outageInfo4);
        outageResponse.setOutageInfoList(outageInfoList);
        return outageResponse;

    }

    private Map<String, String> getDeviceMap(){

        Map<String, String> deviceMap = new HashMap<>();
        deviceMap.put("111183e7-fb90-436b-9951-63392b36bdd2","Battery 1");
        deviceMap.put("86b5c819-6a6c-4978-8c51-a2d810bb9318","Battery 2");

        return deviceMap;
    }

    private OutageResponse getOutageListNotForTheSite(){

        OutageResponse outageResponse = new OutageResponse();

        List<OutageInfo> outageInfoList = new ArrayList<>();
        OutageInfo outageInfo = new OutageInfo();
        outageInfo.setId("111183e7-fb90-436b-9951-63392b36abc8");
        outageInfo.setBegin(KrakenflexDateUtil.parseZoneDate("2022-02-15T11:28:26.735Z"));
        outageInfo.setEnd(KrakenflexDateUtil.parseZoneDate("2022-08-28T03:37:48.568Z"));
        outageInfoList.add(outageInfo);
        outageResponse.setOutageInfoList(outageInfoList);
        return outageResponse;
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

    private JsonNode getJsonNode(List<String> outageJsonList){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(outageJsonList.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }


}
