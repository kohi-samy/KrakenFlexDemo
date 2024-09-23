package com.krakenflex.technicaltest.demo.service;

import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.gateway.KrakenMockOutageClientService;
import com.krakenflex.technicaltest.demo.service.impl.OutageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OutageServiceTest {

    @Mock
    private KrakenMockOutageClientService krakenMockOutageClientService;

    @InjectMocks
    OutageServiceImpl outageServiceImpl;

    @Test
    void fetchAllOutagesSuccessfully(){

        when(krakenMockOutageClientService.retrieveAllOutages()).thenReturn(new OutageResponse());

        outageServiceImpl.fetchAllOutages();

        verify(krakenMockOutageClientService, timeout(1)).retrieveAllOutages();
    }

}
