package com.krakenflex.technicaltest.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnhancedOutageInfo {
    @Schema(name = "device ID", description = "uniquely identifier of a device")
    private String id;
    @Schema(name = "device name", description = "name of a device")
    private String name;
    @Schema(name = "begin", description = "outage start time, (ex. 2022-05-23T12:21:27.377Z)")
    private String begin;
    @Schema(name = "end", description = "outage end time, (ex. 2022-05-23T12:21:27.377Z)")
    private String end;
}
