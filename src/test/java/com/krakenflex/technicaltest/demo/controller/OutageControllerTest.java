package com.krakenflex.technicaltest.demo.controller;

import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.service.OutageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(OutageController.class)
public class OutageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OutageService outageService;

    @Test
    void shouldFetchAllOutages() throws Exception{

        when(outageService.fetchAllOutages()).thenReturn(new OutageResponse());
        mockMvc.perform(get("/v1/outages"))
                .andExpect(status().isOk());

    }
}
