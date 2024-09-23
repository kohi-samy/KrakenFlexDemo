package com.krakenflex.technicaltest.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SiteOutages {
    @Schema(name = "siteOutages", description = "List of enhanced outage of devices installed in site")
    List<EnhancedOutageInfo> siteOutages;
}
