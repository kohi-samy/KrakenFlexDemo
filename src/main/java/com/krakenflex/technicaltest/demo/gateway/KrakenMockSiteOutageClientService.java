package com.krakenflex.technicaltest.demo.gateway;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public interface KrakenMockSiteOutageClientService {

    Object createSiteOutage(JsonNode siteOutages, String site);
}
