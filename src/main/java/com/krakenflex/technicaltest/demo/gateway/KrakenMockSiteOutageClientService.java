package com.krakenflex.technicaltest.demo.gateway;

import com.fasterxml.jackson.databind.JsonNode;

public interface KrakenMockSiteOutageClientService {

    String createSiteOutage(JsonNode siteOutages, String site);
}
