package com.krakenflex.technicaltest.demo.service;

import com.krakenflex.technicaltest.demo.dto.Site;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteClientService;
import com.krakenflex.technicaltest.demo.service.impl.SiteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SiteServiceTest {

    @Mock
    private KrakenMockSiteClientService krakenMockSiteClientService;

    @InjectMocks
    SiteServiceImpl siteServiceImpl;

    @Test
    void fetchAllOutagesSuccessfully(){

        when(krakenMockSiteClientService.fetchSiteInfo(any())).thenReturn(new Site());

        siteServiceImpl.fetchSiteInfo("test");

        verify(krakenMockSiteClientService, timeout(1)).fetchSiteInfo("test");
    }
}
