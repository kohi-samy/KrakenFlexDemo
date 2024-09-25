package com.krakenflex.technicaltest.demo.controller;

import com.krakenflex.technicaltest.demo.dto.Site;
import com.krakenflex.technicaltest.demo.dto.SiteOutages;
import com.krakenflex.technicaltest.demo.exception.AuthorizationException;
import com.krakenflex.technicaltest.demo.exception.OutageServiceException;
import com.krakenflex.technicaltest.demo.exception.RequestLimitException;
import com.krakenflex.technicaltest.demo.service.SiteOutageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/v1")
public class SiteOutageController {

    SiteOutageService siteOutageService;

    public SiteOutageController(SiteOutageService siteOutageService){
        this.siteOutageService = siteOutageService;
    }

    @Operation(summary = "Post enhanced outages for devices installed within specific site", description = "The outages posted should contain a device ID, device name, begin time, and end time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Post success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode =  "403", description = "Post unsuccessful due to unAuth", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationException.class))),
            @ApiResponse(responseCode =  "429", description = "You've exceeded your limit for your API key.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestLimitException.class))),
            @ApiResponse(responseCode =  "404", description = "Resource not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OutageServiceException.class))),
            @ApiResponse(responseCode =  "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RuntimeException.class))),
    })
    @PostMapping("/site-outages/{siteId}")
    public ResponseEntity<Object> publishFilteredSiteOutages(@PathVariable(value = "siteId") String siteId){
        Object response = siteOutageService.filterOutagesOfSiteAndPost(siteId);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*@PostMapping("/manual/site-outages/{siteId}")
    public ResponseEntity<String> createSiteOutagesManual(@RequestBody @NotNull SiteOutages siteOutages, @PathVariable(value = "siteId") String siteId){
        return  new ResponseEntity<>(siteOutageService.createSiteOutage(siteOutages, siteId), HttpStatus.OK);
    }*/

}
