package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.Request.AddLogisticsDto;
import com.drms.disaster_relief.dto.Response.AllLogisticsResponseDto;
import com.drms.disaster_relief.entity.ConsumableLogistics;
import com.drms.disaster_relief.entity.LogisticsProduct;
import com.drms.disaster_relief.entity.ReturnableLogistics;
import com.drms.disaster_relief.enums.LogisticsStatus;
import com.drms.disaster_relief.enums.LogisticsType;
import com.drms.disaster_relief.repository.ConsumableLogisticsRepo;
import com.drms.disaster_relief.repository.LogisticsProductRepo;
import com.drms.disaster_relief.repository.ReturnableLogisticsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsService {
    private final ReturnableLogisticsRepo returnableLogisticsRepo;
    private final ConsumableLogisticsRepo  consumableLogisticsRepo;
    private final LogisticsProductRepo logisticsProductRepo;

    //Constructor
    public LogisticsService(ReturnableLogisticsRepo returnableLogisticsRepo,
                            ConsumableLogisticsRepo consumableLogisticsRepo,
                            LogisticsProductRepo logisticsProductRepo){
        this.returnableLogisticsRepo = returnableLogisticsRepo;
        this.consumableLogisticsRepo = consumableLogisticsRepo;
        this.logisticsProductRepo = logisticsProductRepo;
    }

    //to view all logistics
    public AllLogisticsResponseDto getAllLogistics(){
        List<ReturnableLogistics> returnableLogistics = returnableLogisticsRepo.findAll();
        List<ConsumableLogistics> consumableLogistics = consumableLogisticsRepo.findAll();

        //Initialize and populate response DTO
        AllLogisticsResponseDto logisticsResponseDto = new AllLogisticsResponseDto();
        logisticsResponseDto.setReturnableLogisticsList(returnableLogistics);
        logisticsResponseDto.setConsumableLogisticsList(consumableLogistics);
        //set sizes
        logisticsResponseDto.setReturnableLogisticsSize(returnableLogistics.size());
        logisticsResponseDto.setConsumableLogisticsSize(consumableLogistics.size());

        return logisticsResponseDto;
    }

    public void addLogistics(AddLogisticsDto addLogisticsDto){

        if(addLogisticsDto.getType().name().equals(LogisticsType.CONSUMABLE.name()))
            addConsumableLogistics(addLogisticsDto);
        else if(addLogisticsDto.getType().name().equals(LogisticsType.RETURNABLE.name()))
            addReturnableLogistics(addLogisticsDto);
        else
            System.out.println("Not valid Option");

    }

    private void addConsumableLogistics(AddLogisticsDto addLogisticsDto){
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
    }

    private void addReturnableLogistics(AddLogisticsDto addLogisticsDto){
        LogisticsProduct logisticsProduct = new LogisticsProduct();

        logisticsProduct.setName(addLogisticsDto.getName());
        logisticsProduct.setType(LogisticsType.CONSUMABLE);

        ReturnableLogistics returnableLogistics = new ReturnableLogistics();
        returnableLogistics.setAddedBy(addLogisticsDto.getAddedBy());
        returnableLogistics.setBranch(addLogisticsDto.getBranch());
        returnableLogistics.setYear(addLogisticsDto.getYear());
        returnableLogistics.setProductInfo(logisticsProduct);
        returnableLogistics.setIdNumber(addLogisticsDto.getIdNumber());
        returnableLogistics.setStatus(LogisticsStatus.AVAILABLE);

        logisticsProductRepo.save(logisticsProduct);
        returnableLogisticsRepo.save(returnableLogistics);
    }


}
