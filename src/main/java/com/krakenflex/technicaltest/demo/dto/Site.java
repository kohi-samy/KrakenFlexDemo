package com.krakenflex.technicaltest.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class Site {
    @Schema(name = "Site ID", description = "uniquely identifier of a site")
    String id;
    @Schema(name = "Site name", description = "Site name")
    String name;
    @Schema(name = "devices", description = "List of devices installed in the site")
    List<Devices> devices;
}
