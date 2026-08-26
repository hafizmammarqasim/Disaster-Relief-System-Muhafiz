package com.drms.disaster_relief.location.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CityDTO {
    private String cityName;
    private UUID provinceId;
}