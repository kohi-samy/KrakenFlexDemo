package com.krakenflex.technicaltest.demo.service;

import com.krakenflex.technicaltest.demo.dto.SiteOutages;

public interface SiteOutageService {

//    String createSiteOutage(SiteOutages siteOutages, String site);

    String filterOutagesOfSiteAndPost(String site);
}
