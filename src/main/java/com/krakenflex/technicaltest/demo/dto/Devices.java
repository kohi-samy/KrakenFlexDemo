package com.krakenflex.technicaltest.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class Devices {

    @Schema(name = "device ID", description = "uniquely identifier of a device")
    String id;
    @Schema(name = "device name", description = "device name")
    String name;
}
