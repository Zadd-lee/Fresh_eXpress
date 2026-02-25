package com.mink.freshexpress.warehouse.dto;

import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import lombok.Getter;

@Getter
public class WarehouseLocationResponseDto {

    private Long id;

    private String temperature;
    private String code;
    private boolean isActive;

    public WarehouseLocationResponseDto(WarehouseLocation warehouseLocation) {
        this.id = warehouseLocation.getId();
        this.temperature = warehouseLocation.getTemperature().name();
        this.code = warehouseLocation.getCode();
        this.isActive = warehouseLocation.isActive();
    }
}
