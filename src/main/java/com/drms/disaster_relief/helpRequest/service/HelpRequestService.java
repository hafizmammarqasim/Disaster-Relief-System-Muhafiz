package com.drms.disaster_relief.helpRequest.service;

import com.drms.disaster_relief.helpRequest.repo.HelpRequestLogRepository;
import com.drms.disaster_relief.helpRequest.repo.HelpRequestRepository;
import com.drms.disaster_relief.location.repo.CityRepository;
import com.drms.disaster_relief.auth.repo.UserRepository;
import com.drms.disaster_relief.helpRequest.dto.HelpRequestDTO;
import com.drms.disaster_relief.helpRequest.dto.HelpRequestLogDTO;
import com.drms.disaster_relief.helpRequest.dto.HelpRequestResponseDTO;
import com.drms.disaster_relief.location.entity.City;
import com.drms.disaster_relief.helpRequest.entity.HelpRequest;
import com.drms.disaster_relief.helpRequest.entity.HelpRequestLog;
import com.drms.disaster_relief.auth.entity.User;
import com.drms.disaster_relief.helpRequest.enums.HelpType;
import com.drms.disaster_relief.helpRequest.enums.RequestStatus;
import com.drms.disaster_relief.helpRequest.enums.UrgencyLevel;
import com.drms.disaster_relief.location.repo.ProvinceRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelpRequestService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;
    private final HelpRequestRepository helpRequestRepository;
    private final HelpRequestLogRepository helpRequestLogRepository;

    public HelpRequestService(UserRepository userRepository,
                       CityRepository cityRepository,
                       ProvinceRepository provinceRepository,
                       HelpRequestRepository helpRequestRepository,
                       HelpRequestLogRepository helpRequestLogRepo) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.provinceRepository = provinceRepository;
        this.helpRequestRepository = helpRequestRepository;
        this.helpRequestLogRepository = helpRequestLogRepo;
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
        helpRequest.setHelpType(HelpType.valueOf(helpRequestDTO.getHelpType()));
        helpRequest.setUrgencyLevel(UrgencyLevel.valueOf(helpRequestDTO.getUrgencyLevel()));
        helpRequest.setDescription(helpRequestDTO.getDescription());
        helpRequest.setStatus(RequestStatus.PENDING);

        HelpRequest savedRequest = helpRequestRepository.save(helpRequest);

        HelpRequestLog helpRequestLog = new HelpRequestLog();
        helpRequestLog.setRequest(savedRequest);
        helpRequestLog.setStatus(RequestStatus.PENDING);
        helpRequestLog.setRemarks("Emergency request successfully submitted by user.");

        helpRequestLogRepository.save(helpRequestLog);
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
        dto.setHelpType(request.getHelpType().name());
        dto.setUrgencyLevel(request.getUrgencyLevel().name());
        dto.setStatus(request.getStatus().name());
        dto.setArea(request.getArea());
        dto.setDescription(request.getDescription());
        dto.setCreatedAt(request.getCreatedAt());

        List<HelpRequestLogDTO> logDTOs = request.getRequestLog().stream().map(log -> {
            HelpRequestLogDTO logDto = new HelpRequestLogDTO();
            logDto.setStatus(log.getStatus().name());
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
