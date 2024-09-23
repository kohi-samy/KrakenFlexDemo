package com.krakenflex.technicaltest.demo.service;

import com.krakenflex.technicaltest.demo.dto.Devices;
import com.krakenflex.technicaltest.demo.dto.OutageInfo;
import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.dto.Site;
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
import java.util.List;

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
    void filterOutagesOfSiteAndPostSuccessfully(){

        ReflectionTestUtils.setField(siteOutageImpl,"cutOffDate", "2022-01-01T00:00:00.000Z");

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(getSite());
        when(krakenMockOutageClientService.retrieveAllOutages()).thenReturn(getOutageList());
        when(krakenMockSiteOutageClientService.createSiteOutage(any(), any())).thenReturn("Successfully added");

        String response= siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");

        assertEquals("Successfully added", response);

    }

    @Test
    void filterOutagesOfSiteAndPost_Exception_siteInfoNull(){

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(null);
        try {
            String response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
        }catch(Exception e){
            assertEquals("Site info not available : test-Site", e.getMessage());
        }

    }

    @Test
    void filterOutagesOfSiteAndPost_Exception_deviceEmpty(){

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(getSiteWithNoDevice());
        try {
            String response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
        }catch(Exception e){
            assertEquals("Device not available in this site: test-Site", e.getMessage());
        }

    }

    @Test
    void filterOutagesOfSiteAndPost_Exception_OutagesNull(){

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(getSite());
        when(krakenMockOutageClientService.retrieveAllOutages()).thenReturn(null);

        try {
            String response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
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
            String response = siteOutageImpl.filterOutagesOfSiteAndPost("test-Site");
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
        List<Devices> devicesList = new ArrayList<>();
        devicesList.add(device);
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

    private OutageResponse getOutageList(){

        OutageResponse outageResponse = new OutageResponse();

        List<OutageInfo> outageInfoList = new ArrayList<>();
        OutageInfo outageInfo = new OutageInfo();
        outageInfo.setId("111183e7-fb90-436b-9951-63392b36bdd2");
        outageInfo.setBegin(KrakenflexDateUtil.parseZoneDate("2022-02-15T11:28:26.735Z"));
        outageInfo.setEnd(KrakenflexDateUtil.parseZoneDate("2022-08-28T03:37:48.568Z"));
        outageInfoList.add(outageInfo);
        outageResponse.setOutageInfoList(outageInfoList);
        return outageResponse;

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


}
