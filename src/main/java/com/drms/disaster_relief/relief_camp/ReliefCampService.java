package com.drms.disaster_relief.relief_camp;

import jakarta.transaction.Transactional;

public class ReliefCampService {

   private final ReliefCampRepository reliefCampRepository;

   public ReliefCampService(ReliefCampRepository reliefCampRepository){
       this.reliefCampRepository = reliefCampRepository;
   }

   @Transactional
   public boolean createReliefCamp(ReliefCampDto reliefCampDto){
       ReliefCamp camp = new ReliefCamp();

       camp.setCampName(reliefCampDto.getCampName());
       camp.setCity(reliefCampDto.getCity());
       camp.setAvailableWater(reliefCampDto.isAvailableWater());
       camp.setAvailableMedical(reliefCampDto.isAvailableMedical());
       camp.setAvailableFood(reliefCampDto.isAvailableFood());
       camp.setAvailableShelter(reliefCampDto.isAvailableShelter());

       reliefCampRepository.save(camp);
       return true;
   }

}
