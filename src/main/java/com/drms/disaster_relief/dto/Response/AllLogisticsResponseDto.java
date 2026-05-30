package com.drms.disaster_relief.dto.Response;

import com.drms.disaster_relief.entity.ConsumableLogistics;
import com.drms.disaster_relief.entity.ReturnableLogistics;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Data
public class AllLogisticsResponseDto {

    private List<ReturnableLogistics> returnableLogisticsList;

    private List<ConsumableLogistics> consumableLogisticsList;

    private int returnableLogisticsSize;
    private int consumableLogisticsSize;
}
