package com.mink.freshexpress.warehouse.service.imp;

import com.mink.freshexpress.warehouse.dto.WarehouseCreateRequestDto;
import com.mink.freshexpress.warehouse.model.Warehouse;
import com.mink.freshexpress.warehouse.repository.WarehouseRepository;
import com.mink.freshexpress.warehouse.service.WarehouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Transactional
    @Override
    public void create(WarehouseCreateRequestDto dto) {
        Warehouse warehouse = dto.toEntity();
        warehouseRepository.save(warehouse);

    }
}
