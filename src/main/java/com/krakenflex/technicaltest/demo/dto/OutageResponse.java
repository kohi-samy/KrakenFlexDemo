package com.krakenflex.technicaltest.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutageResponse {
    @Schema(name = "outageInfo", description = "List of outageInfo")
    private List<OutageInfo> outageInfoList;
}
