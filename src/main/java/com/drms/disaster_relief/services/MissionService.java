package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.Request.AssignLogisticsDto;
import com.drms.disaster_relief.dto.Request.CreateMissionDto;
import com.drms.disaster_relief.dto.Request.CrewAssignmentDto;
import com.drms.disaster_relief.dto.Response.AssignedCrewMemberDto;
import com.drms.disaster_relief.dto.Response.LogisticsAssignedResponseDto;
import com.drms.disaster_relief.dto.Response.MissionDispatchResponseDto;
import com.drms.disaster_relief.entity.*;
import com.drms.disaster_relief.enums.*;
import com.drms.disaster_relief.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MissionService {

    MissionRepository missionRepository;
    CrewAssignmentRepository crewAssignmentRepo;
    EmployeeRepository employeeRepository;
    CityRepository cityRepository;
    BranchRepository branchRepository;
    ReturnableLogisticsRepo returnableLogisticsRepo;
    ConsumableLogisticsRepo consumableLogisticsRepo;
    ReturnableLogisticsAssignmentRepository returnableAssignmentRepo;
    ConsumableLogisticsAssignmentRepo consumableAssignmentRepo;

    //constructor Injection
    public MissionService(MissionRepository missionRepository,
                          CrewAssignmentRepository crewAssignmentRepo,
                          EmployeeRepository employeeRepository,
                          CityRepository cityRepository,
                          BranchRepository branchRepository,
                          ReturnableLogisticsRepo returnableLogisticsRepo,
                          ConsumableLogisticsRepo consumableLogisticsRepo,
                          ReturnableLogisticsAssignmentRepository returnableAssignmentRepo,
                          ConsumableLogisticsAssignmentRepo consumableAssignmentRepo){
        this.missionRepository = missionRepository;
        this.crewAssignmentRepo = crewAssignmentRepo;
        this.employeeRepository = employeeRepository;
        this.cityRepository = cityRepository;
        this.branchRepository = branchRepository;
        this.returnableLogisticsRepo = returnableLogisticsRepo;
        this.consumableLogisticsRepo = consumableLogisticsRepo;
        this.returnableAssignmentRepo = returnableAssignmentRepo;
        this.consumableAssignmentRepo = consumableAssignmentRepo;
    }

    public List<Mission> getMissionByEmployee(UUID employeeId){
       return missionRepository.findByCrewAssignments_Employee_EmployeeId(employeeId);
    }

    @Transactional
    public Mission createMission(CreateMissionDto missionDto){
        Mission mission = new Mission();

        mission.setMissionName(missionDto.getMissionName());
        mission.setArea(missionDto.getArea());
        mission.setType(missionDto.getType());
        mission.setGuidelines(missionDto.getGuidelines());
        mission.setLocationLat(missionDto.getLocationLat());
        mission.setLocationLng(missionDto.getLocationLng());
        mission.setStatus(MissionStatus.ABSTRACT);

        if(missionDto.getCityId() != null) mission.setCity(cityRepository.getReferenceById(missionDto.getCityId()));
        if(missionDto.getBranchId() != null) mission.setBranch(branchRepository.getReferenceById(missionDto.getBranchId()));


        return missionRepository.save(mission);
    }

    @Transactional
    public MissionDispatchResponseDto crewAssignment(CrewAssignmentDto crewAssignmentDto){

       Mission mission = missionRepository.findById(crewAssignmentDto.getMissionId())
               .orElseThrow(()->  new IllegalStateException("Mission Not Found"));

       List<Employee> crewList = new ArrayList<>();

       if(crewAssignmentDto.getDiversNeeded()>0)
           crewList.addAll(getAvailableCrew(EmployeeSpecialization.DIVER, crewAssignmentDto.getDiversNeeded()));

       if(crewAssignmentDto.getDriversNeeded() > 0)
            crewList.addAll(getAvailableCrew(EmployeeSpecialization.DRIVER, crewAssignmentDto.getDriversNeeded()));

       if(crewAssignmentDto.getFirefightersNeeded() > 0)
           crewList.addAll(getAvailableCrew(EmployeeSpecialization.FIREFIGHTER, crewAssignmentDto.getFirefightersNeeded()));

       if(crewAssignmentDto.getSarSpecialistsNeeded() > 0)
           crewList.addAll(getAvailableCrew(EmployeeSpecialization.SAR_SPECIALIST, crewAssignmentDto.getSarSpecialistsNeeded()));

       if(crewAssignmentDto.getMedicsNeeded() > 0)
           crewList.addAll(getAvailableCrew(EmployeeSpecialization.MEDIC, crewAssignmentDto.getMedicsNeeded()));


       //now assign crew in mission
        List<MissionCrewAssignment> savedCrewList = saveCrewAssignment(crewList, mission);

        return buildDispatchResponse(savedCrewList, mission);
    }


    //Save assignment data in crewAssignment Repo...
    private  List<MissionCrewAssignment> saveCrewAssignment(List<Employee> crewList, Mission mission){

        List<MissionCrewAssignment> crewAssignmentList = new ArrayList<>();
        for(Employee crew: crewList){
            MissionCrewAssignment tempMissionCrew = new MissionCrewAssignment();
            tempMissionCrew.setMission(mission);
            tempMissionCrew.setEmployee(crew);

            //updating the employee status in employee Table (repo)
            crew.setEmployeeStatus(EmployeeWorkingStatus.TASK_ASSIGNED);
            employeeRepository.save(crew);

            //Saving assignment data in crew assignment repo
            crewAssignmentRepo.save(tempMissionCrew);

            //saving data in list for further use (dispatching)
            crewAssignmentList.add(tempMissionCrew);
        }

        //also add crew assignment in mission
        mission.setCrewAssignments(crewAssignmentList);

        //update mission status
        mission.setStatus(MissionStatus.CREW_ASSIGNED);
        return crewAssignmentList;
    }

    //Getting one employee type data from repository
    private List<Employee> getAvailableCrew(EmployeeSpecialization name, int quantity) throws IllegalStateException {

        Pageable page = PageRequest.of(0, quantity);

        //Get data in form of page from repo
        Page<Employee> crewPage = employeeRepository.findBySpecializationAndEmployeeStatus(
                name, EmployeeWorkingStatus.Available, page);

        List<Employee> specializedCrew = crewPage.getContent();
        //Check crew was available or not
        if(specializedCrew.size() < quantity){
            throw new IllegalStateException("less employees in db");
        }

        return specializedCrew;
    }

    //Add mission and crew assignment data in response dto
    private MissionDispatchResponseDto buildDispatchResponse(List<MissionCrewAssignment> assignments, Mission mission) {
        // 1. Create the main response object
        MissionDispatchResponseDto response = new MissionDispatchResponseDto();
        response.setMissionId(mission.getMissionId());
        response.setMissionName(mission.getMissionName());
        response.setStatus(mission.getStatus());

        // to avoid NullPointerExceptions if city/branch are not set yet
        if (mission.getCity() != null) {
            response.setCityName(mission.getCity().getCityName());
        }
        if (mission.getBranch() != null) {
            response.setBranchName(mission.getBranch().getBranchName());
        }

        // 2. Create the list for the assigned crew members
        List<AssignedCrewMemberDto> crewDtos = new ArrayList<>();
        for (MissionCrewAssignment assignment : assignments) {
            //Instantiation
            Employee employee = assignment.getEmployee();
            AssignedCrewMemberDto crewDto = new AssignedCrewMemberDto();
            //Set data
            crewDto.setEmployeeId(employee.getEmployeeId());
            crewDto.setFirstName(employee.getFirstName());
            crewDto.setLastName(employee.getLastName());
            crewDto.setSpecialization(employee.getSpecialization());
            crewDto.setPhoneNumber(employee.getPhoneNumber());
            crewDtos.add(crewDto);
        }

        // 3. Add the crew list to the main response and return it
        response.setAssignedCrew(crewDtos);
        return response;
    }

    @Transactional
    public LogisticsAssignedResponseDto assignLogistics(AssignLogisticsDto dto) {
        Mission mission = missionRepository.findById(dto.getMissionId())
                .orElseThrow(() -> new IllegalStateException("Mission Not Found"));

        int returnableCount = 0;
        int consumableCount = 0;

        // 1. ASSIGN RETURNABLES (Dynamic Selection)
        if (dto.getReturnables() != null) {
            for (AssignLogisticsDto.ReturnableRequest req : dto.getReturnables()) {
                Pageable limit = PageRequest.of(0, req.getQuantityNeeded());

                List<ReturnableLogistics> assetsToAssign = returnableLogisticsRepo
                        .findByProductInfo_ProductIdAndStatus(req.getProductId(), LogisticsStatus.AVAILABLE, limit)
                        .getContent();

                if (assetsToAssign.size() < req.getQuantityNeeded()) {
                    throw new IllegalStateException("Not enough assets available for product ID: " + req.getProductId());
                }

                for (ReturnableLogistics asset : assetsToAssign) {
                    asset.setStatus(LogisticsStatus.IN_USE); // Update status!
                    returnableLogisticsRepo.save(asset);

                    ReturnableLogisticsAssignment assignment = new ReturnableLogisticsAssignment();
                    assignment.setMission(mission);
                    assignment.setLogistics(asset);
                    assignment.setReturnStatus(LogisticsReturnStatus.IN_USE);
                    returnableAssignmentRepo.save(assignment);
                    returnableCount++;
                }
                System.out.println(returnableCount);
            }
        }

        // 2. ASSIGN CONSUMABLES (Direct Row Update)
        if (dto.getConsumables() != null) {
            for (AssignLogisticsDto.ConsumableRequest req : dto.getConsumables()) {
                ConsumableLogistics item = consumableLogisticsRepo.findById(req.getLogisticsId())
                        .orElseThrow(() -> new IllegalStateException("Consumable not found"));

                // Convert String to Int for math
                int availableUnits = Integer.parseInt(item.getQuantityOfUnits());

                if (availableUnits < req.getRequestedUnits()) {
                    throw new IllegalStateException("Not enough units available for: " + item.getProductInfo().getName());
                }

                // Deduct and save back as String
                int newUnits = availableUnits - req.getRequestedUnits();
                item.setQuantityOfUnits(String.valueOf(newUnits));
                consumableLogisticsRepo.save(item);

                ConsumableLogisticsAssignment assignment = new ConsumableLogisticsAssignment();
                assignment.setMission(mission);
                assignment.setLogistics(item);
                assignment.setQuantityAssigned(req.getRequestedUnits());
                consumableAssignmentRepo.save(assignment);
                consumableCount++;
            }
        }

        System.out.println("Reached checkpoint");
        // 3. FINALIZE MISSION
        mission.setStatus(MissionStatus.IN_PROGRESS);
        missionRepository.save(mission);

        LogisticsAssignedResponseDto response = new LogisticsAssignedResponseDto();
        response.setMissionId(mission.getMissionId());
        response.setMissionName(mission.getMissionName());
        response.setReturnablesAssigned(returnableCount);
        response.setConsumablesAssigned(consumableCount);
        response.setMessage("Logistics completely assigned. Mission is IN_PROGRESS.");

        return response;
    }
}

