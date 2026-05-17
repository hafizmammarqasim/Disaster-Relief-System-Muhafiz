package com.drms.disaster_relief.dto.Request;
import com.drms.disaster_relief.entity.*;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateMissionDto {


    private UUID requestId;
    private UUID cityId;
    private UUID branchId;
    private UUID createdById;

        private String missionName;

        private String type;

        private String area;

        private float locationLat;

        private float locationLng;

        private String helpType;

        private String urgencyLevel;

        private String guidelines;
}
