package com.mink.freshexpress.warehouse.service;

import com.mink.freshexpress.warehouse.dto.WarehouseCreateRequestDto;
import jakarta.validation.Valid;

public interface WarehouseService {
    void create(@Valid WarehouseCreateRequestDto dto);
}
