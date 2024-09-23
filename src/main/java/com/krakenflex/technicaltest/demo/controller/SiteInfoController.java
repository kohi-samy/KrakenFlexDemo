package com.krakenflex.technicaltest.demo.controller;

import com.krakenflex.technicaltest.demo.dto.OutageResponse;
import com.krakenflex.technicaltest.demo.dto.Site;
import com.krakenflex.technicaltest.demo.exception.AuthorizationException;
import com.krakenflex.technicaltest.demo.exception.OutageServiceException;
import com.krakenflex.technicaltest.demo.service.SiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/v1")
@Validated
public class SiteInfoController {

    private SiteService siteService;

    public SiteInfoController(SiteService siteService){
        this.siteService = siteService;
    }

    @Operation(summary = "Returns information about a specific site.", description = "The site information contains the ID and name of the site. It also contains a list of devices that make up the site.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Site returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class))),
            @ApiResponse(responseCode =  "403", description = "Site Not Available due to unAuth", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationException.class))),
            @ApiResponse(responseCode =  "404", description = "Site not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OutageServiceException.class))),
            @ApiResponse(responseCode =  "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RuntimeException.class))),

    })
    @GetMapping("/site-info/{site}")
    public ResponseEntity<Site> fetchSiteInfo(@PathVariable(value = "site") @Valid  String site){
        return new ResponseEntity<>(siteService.fetchSiteInfo(site), HttpStatus.OK);
    }

}
