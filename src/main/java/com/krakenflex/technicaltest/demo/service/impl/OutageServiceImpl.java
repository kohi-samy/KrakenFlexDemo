package com.krakenflex.technicaltest.demo.service.impl;

import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockOutageClientService;
import com.krakenflex.technicaltest.demo.service.OutageService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OutageServiceImpl implements OutageService {

    private KrakenMockOutageClientService krakenMockOutageClientService;

    public OutageServiceImpl(KrakenMockOutageClientService krakenMockOutageClientService){
        this.krakenMockOutageClientService = krakenMockOutageClientService;
    }
    @Override
    public OutageResponse fetchAllOutages() {

        return krakenMockOutageClientService.retrieveAllOutages();

    }
}
