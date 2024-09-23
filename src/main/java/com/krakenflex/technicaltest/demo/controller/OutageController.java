package com.krakenflex.technicaltest.demo.controller;

import com.krakenflex.technicaltest.demo.dto.OutageInfo;
import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.exception.OutageServiceException;
import com.krakenflex.technicaltest.demo.service.OutageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/v1")
public class OutageController {

    private OutageService outageService;

    public OutageController(OutageService outageService){
        this.outageService = outageService;
    }

    @Operation(summary = "Returns all outages in our system.", description = "An outage is when a device can no longer provide service and is declared as offline. Each outage consists of a device ID, begin time, and end time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Outages returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OutageResponse.class))),
            @ApiResponse(responseCode =  "403", description = "Outages Not Available due to unAuth", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OutageServiceException.class))),
            @ApiResponse(responseCode =  "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OutageServiceException.class))),

    })
    @GetMapping("/outages")
    public ResponseEntity<OutageResponse> fetchAllOutages(){

        OutageResponse outageResponse = outageService.fetchAllOutages();
        return new ResponseEntity<>(outageResponse, HttpStatus.OK);

    }




}
