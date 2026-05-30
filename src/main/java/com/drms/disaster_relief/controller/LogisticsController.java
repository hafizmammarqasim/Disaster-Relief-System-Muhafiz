package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.Request.AddLogisticsDto;
import com.drms.disaster_relief.dto.Response.AllLogisticsResponseDto;
import com.drms.disaster_relief.services.LogisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logistics")
public class LogisticsController {

    private final LogisticsService logisticsService;

    public LogisticsController(LogisticsService logisticsService){
        this.logisticsService = logisticsService;
    }

    //As we are not using inheritance now, so in DTO we need to use both logistics type
    // and on frontend get them...
    @GetMapping("/view")
    public ResponseEntity<?> viewAllLogistics(){
         AllLogisticsResponseDto logisticsResponseDto = logisticsService.getAllLogistics();

         return new ResponseEntity<>(logisticsResponseDto, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<?> addLogistics(AddLogisticsDto addLogisticsDto){
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }



}
