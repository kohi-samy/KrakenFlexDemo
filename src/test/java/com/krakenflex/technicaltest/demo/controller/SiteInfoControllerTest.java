package com.krakenflex.technicaltest.demo.controller;


import com.krakenflex.technicaltest.demo.dto.Site;
import com.krakenflex.technicaltest.demo.service.SiteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SiteInfoController.class)
public class SiteInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteService siteService;

    @Test
    void shouldFetchSiteInfo() throws Exception{

        when(siteService.fetchSiteInfo(any())).thenReturn(new Site());
        mockMvc.perform(get(String.format("/v1/site-info/%site", "testSite")))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
