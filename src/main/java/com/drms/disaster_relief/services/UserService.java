package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.HelpRequestDTO;
import com.drms.disaster_relief.dto.HelpRequestLogDTO;
import com.drms.disaster_relief.dto.HelpRequestResponseDTO;
import com.drms.disaster_relief.entity.City;
import com.drms.disaster_relief.entity.HelpRequest;
import com.drms.disaster_relief.entity.HelpRequestLog;
import com.drms.disaster_relief.entity.User;
import com.drms.disaster_relief.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;
    private final HelpRequestRepository helpRequestRepository;
    private final HelpRequestLog helpRequestLog;
    private final HelpRequestLogRepository helpRequestLoglogRepository;

    public UserService(UserRepository userRepository, CityRepository cityRepository, ProvinceRepository provinceRepository,
                       HelpRequestRepository helpRequestRepository, HelpRequestLog helpRequestLog, HelpRequestLogRepository helpRequestLoglogRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.provinceRepository = provinceRepository;
        this.helpRequestRepository = helpRequestRepository;
        this.helpRequestLog = helpRequestLog;
        this.helpRequestLoglogRepository = helpRequestLoglogRepository;
    }


    @Transactional
    public String createHelpRequest(HelpRequestDTO helpRequestDTO) {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(currentUserEmail).orElseThrow(()-> new RuntimeException("User with email: "+ currentUserEmail + " not found"));

        City city = cityRepository.findById(helpRequestDTO.getCityId()).orElseThrow(()-> new RuntimeException("City not found"));

        HelpRequest helpRequest = new HelpRequest();

        helpRequest.setUser(user);
        helpRequest.setCityId(city);
        helpRequest.setArea(helpRequestDTO.getArea());
        helpRequest.setNearestLandmark(helpRequestDTO.getNearestLandmark());
        helpRequest.setLocationLat(helpRequest.getLocationLat());
        helpRequest.setLocationLng(helpRequest.getLocationLng());
        helpRequest.setHelpType(helpRequestDTO.getHelpType());
        helpRequest.setUrgencyLevel(helpRequestDTO.getUrgencyLevel());
        helpRequest.setDescription(helpRequestDTO.getDescription());
        helpRequest.setStatus("PENDING");

        HelpRequest savedRequest = helpRequestRepository.save(helpRequest);

        HelpRequestLog helpRequestLog = new HelpRequestLog();
        helpRequestLog.setRequest(savedRequest);
        helpRequestLog.setStatus("PENDING");
        helpRequestLog.setRemarks("Emergency request successfully submitted by user.");

        helpRequestLoglogRepository.save(helpRequestLog);
        return "Your Help Request is submitted successfully";
    }


    public List<HelpRequestResponseDTO> getMyHelpRequests() {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(currentUserEmail).orElseThrow(()-> new RuntimeException("User with email: "+ currentUserEmail + " not found"));

        List<HelpRequest> requests = helpRequestRepository.findByUserUserId(user.getUserId());

        return requests.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    private HelpRequestResponseDTO convertToResponseDTO(HelpRequest request) {
        HelpRequestResponseDTO dto = new HelpRequestResponseDTO();
        dto.setRequestId(request.getRequestId());
        dto.setHelpType(request.getHelpType());
        dto.setUrgencyLevel(request.getUrgencyLevel());
        dto.setStatus(request.getStatus());
        dto.setArea(request.getArea());
        dto.setDescription(request.getDescription());
        dto.setCreatedAt(request.getCreatedAt());

        List<HelpRequestLogDTO> logDTOs = request.getRequestLog().stream().map(log -> {
            HelpRequestLogDTO logDto = new HelpRequestLogDTO();
            logDto.setStatus(log.getStatus());
            logDto.setRemarks(log.getRemarks());
            logDto.setChangedAt(log.getChangedAt());
            // Show who helped (e.g., "City Admin" or "Rescue Team 1")
            if (log.getAddedBy() != null) {
                logDto.setAddedByName(log.getAddedBy().getFirstName());
            }
            return logDto;
        }).collect(Collectors.toList());

        dto.setHistory(logDTOs);

        return dto;
    }


}
