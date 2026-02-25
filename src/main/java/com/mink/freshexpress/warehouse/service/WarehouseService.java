package com.mink.freshexpress.warehouse.service;

import com.mink.freshexpress.warehouse.dto.WarehouseCreateRequestDto;
import com.mink.freshexpress.warehouse.dto.WarehouseLocationCreateRequestDto;
import com.mink.freshexpress.warehouse.dto.WarehouseResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface WarehouseService {
    void create(@Valid WarehouseCreateRequestDto dto);

    void createLocation(Long id, @Valid List<WarehouseLocationCreateRequestDto> dto);

    WarehouseResponseDto find(Long id);
}
