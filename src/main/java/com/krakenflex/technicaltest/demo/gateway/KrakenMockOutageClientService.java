package com.krakenflex.technicaltest.demo.gateway;

import com.krakenflex.technicaltest.demo.dto.OutageInfo;
import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public interface KrakenMockOutageClientService {

    OutageResponse retrieveAllOutages();
}
