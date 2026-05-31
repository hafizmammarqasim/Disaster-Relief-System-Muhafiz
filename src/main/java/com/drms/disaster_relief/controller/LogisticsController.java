package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.Request.AddLogisticsDto;
import com.drms.disaster_relief.dto.Response.AllLogisticsResponseDto;
import com.drms.disaster_relief.dto.Response.DispatchCatalogDto;
import com.drms.disaster_relief.dto.Response.LogisticsDashboardDto;
import com.drms.disaster_relief.dto.Response.LogisticsDetailDto;
import com.drms.disaster_relief.services.LogisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/logistics")
public class LogisticsController {

    private final LogisticsService logisticsService;

    public LogisticsController(LogisticsService logisticsService){
        this.logisticsService = logisticsService;
    }

    @GetMapping("/manage-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLogisticsManagementDashboard() {

        LogisticsDashboardDto dashboardData = logisticsService.getDashboardData();

        return new ResponseEntity<>(dashboardData, HttpStatus.OK);
    }

    //As we are not using inheritance now, so in DTO we need to use both logistics type
    // and on frontend get them...
    @GetMapping("/view")
    public ResponseEntity<?> viewAllLogistics(){
         AllLogisticsResponseDto logisticsResponseDto = logisticsService.getAllLogistics();

         return new ResponseEntity<>(logisticsResponseDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<?> addLogistics(@RequestBody AddLogisticsDto addLogisticsDto){
        try {
           LogisticsDetailDto dto = logisticsService.addLogistics(addLogisticsDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Call this api before showing the section to assign logistics to mission...
    @GetMapping("/branch/{branchId}/dispatch-summary")
    public ResponseEntity<DispatchCatalogDto> getDispatchSummaryByBranch(@PathVariable UUID branchId) {
        DispatchCatalogDto branchCatalog = logisticsService.getCatalogForDispatchByBranch(branchId);
        return new ResponseEntity<>(branchCatalog, HttpStatus.OK);
    }


}
