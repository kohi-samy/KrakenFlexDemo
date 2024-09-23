package com.krakenflex.technicaltest.demo.controller;


import com.krakenflex.technicaltest.demo.service.SiteOutageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SiteOutageController.class)
public class SiteOutageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SiteOutageService siteOutageService;

    @Test
    void shouldPublishFilteredSiteOutages() throws Exception{

        when(siteOutageService.filterOutagesOfSiteAndPost(any())).thenReturn("Unknown");
        mockMvc.perform(post(String.format("/v1/site-outages/%site", "testSite"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
