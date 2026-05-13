package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.CreateMissionDto;
import com.drms.disaster_relief.entity.Employee;
import com.drms.disaster_relief.entity.Mission;
import com.drms.disaster_relief.entity.MissionCrewAssignment;
import com.drms.disaster_relief.repository.MissionRepository;
import enums.MissionStatus;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MissionService {
    MissionRepository missionRepository;

    public MissionService(MissionRepository missionRepository){
        this.missionRepository = missionRepository;
    }

    public List<Mission> getMissionByEmployee(UUID employeeId){
       return missionRepository.findByMissionCrewAssignmentEmployeeEmployeeId(employeeId);
    }

    @Transactional
    public Mission createMission(CreateMissionDto missionDto){

        Mission mission = new Mission();

        mission.setMissionName(missionDto.getMissionName());
        mission.setBranch(mission.getBranch());
        mission.setArea(missionDto.getArea());
        mission.setType(missionDto.getType());
        mission.setCity(missionDto.getCity());
        mission.setGuidelines(missionDto.getGuidelines());
        mission.setLocationLat(missionDto.getLocationLat());
        mission.setLocationLng(missionDto.getLocationLng());
        mission.setStatus(MissionStatus.ABSTRACT);

        return mission;
    }

    private List<MissionCrewAssignment> assignCrew(Mission mission, UUID employeeId){
        return null;
    }
}
