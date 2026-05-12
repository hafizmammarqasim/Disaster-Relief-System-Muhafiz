package com.drms.disaster_relief.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CityDTO {
    private String cityName;
    private UUID provinceId;
}