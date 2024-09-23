package com.krakenflex.technicaltest.demo.service;

import com.krakenflex.technicaltest.demo.dto.OutageInfo;
import com.krakenflex.technicaltest.demo.dto.OutageResponse;

public interface OutageService {

    OutageResponse fetchAllOutages();
}
