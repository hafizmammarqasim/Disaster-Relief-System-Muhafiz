package com.drms.disaster_relief.logistics.service;

import com.drms.disaster_relief.logistics.dto.*;
import com.drms.disaster_relief.logistics.enums.LogisticsStatus;
import com.drms.disaster_relief.logistics.enums.LogisticsType;
import com.drms.disaster_relief.logistics.entity.ConsumableLogistics;
import com.drms.disaster_relief.logistics.entity.LogisticsProduct;
import com.drms.disaster_relief.logistics.entity.ReturnableLogistics;
import com.drms.disaster_relief.logistics.repo.ConsumableLogisticsRepo;
import com.drms.disaster_relief.logistics.repo.LogisticsProductRepo;
import com.drms.disaster_relief.logistics.repo.ReturnableLogisticsRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogisticsService {
    private final ReturnableLogisticsRepo returnableLogisticsRepo;
    private final ConsumableLogisticsRepo consumableLogisticsRepo;
    private final LogisticsProductRepo logisticsProductRepo;

    //Constructor
    public LogisticsService(ReturnableLogisticsRepo returnableLogisticsRepo,
                            ConsumableLogisticsRepo consumableLogisticsRepo,
                            LogisticsProductRepo logisticsProductRepo){
        this.returnableLogisticsRepo = returnableLogisticsRepo;
        this.consumableLogisticsRepo = consumableLogisticsRepo;
        this.logisticsProductRepo = logisticsProductRepo;
    }


    public LogisticsDashboardDto getDashboardData() {
        LogisticsDashboardDto dashboard = new LogisticsDashboardDto();

        // 1. Get the Statistics
        dashboard.setTotalConsumableItems(consumableLogisticsRepo.count());
        dashboard.setTotalReturnableAssets(returnableLogisticsRepo.count());

        // Count only the ones marked as AVAILABLE
        dashboard.setAvailableReturnableAssets(returnableLogisticsRepo.countByStatus(LogisticsStatus.AVAILABLE));

        // 2. Get the top 5 of each (Using the Slicing trick!)
        PageRequest limitOfFive = PageRequest.of(0, 5); // Page 0, Size 5

        // Get 5 consumables
        List<ConsumableLogistics> top5Consumables = consumableLogisticsRepo.findAll(limitOfFive).getContent();
        dashboard.setRecentConsumables(top5Consumables);

        // Get 5 returnables
        List<ReturnableLogistics> top5Returnables = returnableLogisticsRepo.findAll(limitOfFive).getContent();
        dashboard.setRecentReturnables(top5Returnables);

        return dashboard;
    }


        //to view all logistics
        public AllLogisticsResponseDto getAllLogistics() {
            List<ReturnableLogistics> returnableLogistics = returnableLogisticsRepo.findAll();
            List<ConsumableLogistics> consumableLogistics = consumableLogisticsRepo.findAll();

            // 1. Map Consumable Entities to Consumable DTOs
            List<ConsumableLogisticsDto> consumableDtos = consumableLogistics.stream().map(item -> {
                ConsumableLogisticsDto dto = new ConsumableLogisticsDto();
                dto.setLogisticsId(item.getLogisticsId());
                dto.setProductName(item.getProductInfo().getName());
                dto.setQuantityPerUnit(item.getQuantityPerUnit());
                dto.setQuantityOfUnits(item.getQuantityOfUnits());
                dto.setExpirationDate(item.getExpirationDate());
                // Extracting friendly names!
                dto.setBranchName(item.getBranch().getBranchName());
                dto.setAddedByName(item.getAddedBy().getFirstName() + " " + item.getAddedBy().getLastName());
                return dto;
            }).collect(Collectors.toList());

            // 2. Map Returnable Entities to Returnable DTOs
            List<ReturnableLogisticsDto> returnableDtos = returnableLogistics.stream().map(item -> {
                ReturnableLogisticsDto dto = new ReturnableLogisticsDto();
                dto.setLogisticsId(item.getLogisticsId());
                dto.setProductName(item.getProductInfo().getName());
                dto.setIdNumber(item.getIdNumber());
                dto.setYear(item.getYear());
                dto.setStatus(item.getStatus());
                // Extracting names!
                dto.setBranchName(item.getBranch().getBranchName());
                dto.setAddedByName(item.getAddedBy().getFirstName() + " " + item.getAddedBy().getLastName());
                return dto;
            }).collect(Collectors.toList());

            // 3. Initialize and populate the final response DTO
            AllLogisticsResponseDto logisticsResponseDto = new AllLogisticsResponseDto();
            logisticsResponseDto.setReturnableLogisticsList(returnableDtos);
            logisticsResponseDto.setConsumableLogisticsList(consumableDtos);

            logisticsResponseDto.setReturnableLogisticsSize(returnableDtos.size());
            logisticsResponseDto.setConsumableLogisticsSize(consumableDtos.size());

            return logisticsResponseDto;
        }
    public LogisticsDetailDto addLogistics(AddLogisticsDto addLogisticsDto){

        if(addLogisticsDto.getType() == null){
            throw new IllegalArgumentException("No Logistics Type were provided");
        }
        if(addLogisticsDto.getType() == LogisticsType.CONSUMABLE)
            return addConsumableLogistics(addLogisticsDto);
        else if(addLogisticsDto.getType() == LogisticsType.RETURNABLE)
            return addReturnableLogistics(addLogisticsDto);
        else
            throw new IllegalArgumentException("Provided Logistics type is not recognised by the system");
    }

    @Transactional
    private LogisticsDetailDto addConsumableLogistics(AddLogisticsDto addLogisticsDto){
        LogisticsProduct logisticsProduct = new LogisticsProduct();

        logisticsProduct.setName(addLogisticsDto.getName());
        logisticsProduct.setType(LogisticsType.CONSUMABLE);

        ConsumableLogistics consumableLogistics = new ConsumableLogistics();

        consumableLogistics.setBranch(addLogisticsDto.getBranch());
        consumableLogistics.setAddedBy(addLogisticsDto.getAddedBy());
        consumableLogistics.setExpirationDate(addLogisticsDto.getExpirationDate());
        consumableLogistics.setQuantityOfUnits(addLogisticsDto.getQuantityOfUnits());
        consumableLogistics.setQuantityPerUnit(addLogisticsDto.getQuantityPerUnit());
        consumableLogistics.setProductInfo(logisticsProduct);

        //Save in database
        logisticsProductRepo.save(logisticsProduct);
        consumableLogisticsRepo.save(consumableLogistics);

        //Prepare Dto for dispatch

        LogisticsDetailDto dto = new LogisticsDetailDto();
        dto.setProductName(logisticsProduct.getName());
        dto.setLogisticsType(LogisticsType.CONSUMABLE);
        dto.setBranchName(consumableLogistics.getBranch().getBranchName());
        dto.setAddedBy(consumableLogistics.getAddedBy().getFirstName() + consumableLogistics.getAddedBy().getLastName());
        dto.setQuantityPerUnit(consumableLogistics.getQuantityPerUnit());
        dto.setQuantityOfUnits(consumableLogistics.getQuantityOfUnits());
        dto.setExpirationDate(consumableLogistics.getExpirationDate());

        return dto;
    }

    @Transactional
    private LogisticsDetailDto addReturnableLogistics(AddLogisticsDto addLogisticsDto){
        //1. Add in product (Category)
        LogisticsProduct logisticsProduct = new LogisticsProduct();

        logisticsProduct.setName(addLogisticsDto.getName());
        logisticsProduct.setType(LogisticsType.RETURNABLE);

        //2. Add unique record of that category
        ReturnableLogistics returnableLogistics = new ReturnableLogistics();
        returnableLogistics.setAddedBy(addLogisticsDto.getAddedBy());
        returnableLogistics.setBranch(addLogisticsDto.getBranch());
        returnableLogistics.setYear(addLogisticsDto.getYear());
        returnableLogistics.setProductInfo(logisticsProduct);
        returnableLogistics.setIdNumber(addLogisticsDto.getIdNumber());
        returnableLogistics.setStatus(LogisticsStatus.AVAILABLE);

        //Save in database
        logisticsProductRepo.save(logisticsProduct);
        returnableLogisticsRepo.save(returnableLogistics);

        //Prepare Dto for dispatch

        LogisticsDetailDto dto = new LogisticsDetailDto();
        dto.setProductName(logisticsProduct.getName());
        dto.setLogisticsType(LogisticsType.RETURNABLE); // Fixed the bug!
        dto.setBranchName(returnableLogistics.getBranch().getBranchName());
        dto.setAddedBy(returnableLogistics.getAddedBy().getFirstName() + returnableLogistics.getAddedBy().getLastName());
        dto.setIdNumber(returnableLogistics.getIdNumber());
        dto.setYear(returnableLogistics.getYear());
        dto.setStatus(returnableLogistics.getStatus());

        return dto;
    }

    //This function returns logistics data for frontend to assign to mission...
    public DispatchCatalogDto getCatalogForDispatchByBranch(UUID branchId) {
        DispatchCatalogDto response = new DispatchCatalogDto();

        // 1. CONSUMABLES
        List<ConsumableLogistics> branchConsumables = consumableLogisticsRepo.findByBranch_BranchId(branchId);
        List<DispatchCatalogDto.ConsumableSummary> consumableSummaries = new ArrayList<>();
        for (ConsumableLogistics item : branchConsumables) {
            DispatchCatalogDto.ConsumableSummary summary = new DispatchCatalogDto.ConsumableSummary();
            summary.setLogisticsId(item.getLogisticsId());
            summary.setProductName(item.getProductInfo().getName());
            summary.setQuantityPerUnit(item.getQuantityPerUnit());
            summary.setAvailableUnits(item.getQuantityOfUnits());
            consumableSummaries.add(summary);
        }
        response.setConsumables(consumableSummaries);

        // 2. RETURNABLES
        List<LogisticsProduct> returnableProducts = logisticsProductRepo.findByType(LogisticsType.RETURNABLE);
        List<DispatchCatalogDto.ReturnableSummary> returnableSummaries = new ArrayList<>();
        for (LogisticsProduct product : returnableProducts) {
            long count = returnableLogisticsRepo.countByProductInfo_ProductIdAndBranch_BranchIdAndStatus(
                    product.getProductId(), branchId, LogisticsStatus.AVAILABLE);

            if (count > 0) {
                DispatchCatalogDto.ReturnableSummary summary = new DispatchCatalogDto.ReturnableSummary();
                summary.setProductId(product.getProductId());
                summary.setProductName(product.getName());
                summary.setAvailableCount(count);
                returnableSummaries.add(summary);
            }
        }
        response.setReturnables(returnableSummaries);

        return response;
    }

}
