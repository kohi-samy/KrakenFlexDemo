package com.krakenflex.technicaltest.demo.service;

import com.krakenflex.technicaltest.demo.dto.SiteOutages;

public interface SiteOutageService {

    Object filterOutagesOfSiteAndPost(String site);

   // String createSiteOutage(SiteOutages siteOutages, String site);
}
