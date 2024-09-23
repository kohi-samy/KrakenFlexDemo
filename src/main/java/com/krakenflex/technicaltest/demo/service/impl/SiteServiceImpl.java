package com.krakenflex.technicaltest.demo.service.impl;

import com.krakenflex.technicaltest.demo.dto.Site;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockSiteClientService;
import com.krakenflex.technicaltest.demo.service.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SiteServiceImpl implements SiteService {

    private KrakenMockSiteClientService krakenMockSiteClientService;

    public SiteServiceImpl(KrakenMockSiteClientService krakenMockSiteClientService){
        this.krakenMockSiteClientService = krakenMockSiteClientService;
    }
    @Override
    public Site fetchSiteInfo(String siteId) {
        return krakenMockSiteClientService.fetchSiteInfo(siteId);
    }
}
