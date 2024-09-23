package com.krakenflex.technicaltest.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutageInfo {
    @Schema(name = "device ID", description = "uniquely identifier of a device")
    private String id;
    @Schema(name = "begin", description = "outage start time, (ex. 2022-05-23T12:21:27.377Z)")
    private ZonedDateTime begin;
    @Schema(name = "end", description = "outage end time, (ex. 2022-05-23T12:21:27.377Z)")
    private ZonedDateTime end;
}
