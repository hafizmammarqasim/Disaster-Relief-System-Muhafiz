package com.drms.disaster_relief.dto;
import com.drms.disaster_relief.entity.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateMissionDto {


        private HelpRequest request;

        private City city;

        private Branch branch;

        private Employee createdBy;

        private String missionName;

        private String type;

        private String area;

        private float locationLat;

        private float locationLng;

        private String helpType;

        private String urgencyLevel;

        private String guidelines;
}
