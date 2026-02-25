package com.mink.freshexpress.warehouse.dto;

import com.mink.freshexpress.warehouse.model.Warehouse;
import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import jakarta.persistence.Column;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WarehouseResponseDto {
    private String id;
    private String name;

    private String address;

    private boolean isActive;

    private List<WarehouseLocationResponseDto> warehouseLocation = new ArrayList<>();

    public WarehouseResponseDto(Warehouse warehouse) {
        this.id = String.valueOf(warehouse.getId());
        this.name = warehouse.getName();
        this.address = warehouse.getAddress();
        this.isActive = warehouse.isActive();
    }

    public void addWarehouseLocationRequestDto(WarehouseLocationResponseDto warehouseLocationResponseDto) {
        this.warehouseLocation.add(warehouseLocationResponseDto);
    }
}
