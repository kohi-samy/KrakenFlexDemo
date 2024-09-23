package com.krakenflex.technicaltest.demo.gateway;

import com.krakenflex.technicaltest.demo.dto.Site;

public interface KrakenMockSiteClientService {

    Site fetchSiteInfo(String site);
}
