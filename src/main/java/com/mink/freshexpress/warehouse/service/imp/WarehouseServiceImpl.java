package com.mink.freshexpress.warehouse.service.imp;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.WarehouseErrorCode;
import com.mink.freshexpress.common.util.Validator;
import com.mink.freshexpress.warehouse.dto.WarehouseCreateRequestDto;
import com.mink.freshexpress.warehouse.dto.WarehouseLocationCreateRequestDto;
import com.mink.freshexpress.warehouse.dto.WarehouseLocationResponseDto;
import com.mink.freshexpress.warehouse.dto.WarehouseResponseDto;
import com.mink.freshexpress.warehouse.model.Warehouse;
import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import com.mink.freshexpress.warehouse.repository.WarehouseLocationRepository;
import com.mink.freshexpress.warehouse.repository.WarehouseRepository;
import com.mink.freshexpress.warehouse.service.WarehouseService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.mink.freshexpress.common.util.Validator.*;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseLocationRepository warehouseLocationRepository;

    @Transactional
    @Override
    public void create(WarehouseCreateRequestDto dto) {
        Warehouse warehouse = dto.toEntity();
        warehouseRepository.save(warehouse);

    }

    @Transactional
    @Override
    public void createLocation(Long id, List<WarehouseLocationCreateRequestDto> dto) {
        //valid
        Warehouse warehouse = valid(warehouseRepository.findById(id), WarehouseErrorCode.NOT_FOUND_WAREHOUSE);

        //요청 내부에 중복 값 확인
        Set<String> codes = new HashSet<>();

        for (WarehouseLocationCreateRequestDto request : dto) {

            String code = request.getCode();
            String normalized = code.trim().toLowerCase();

            if (!codes.add(normalized)) {
                throw new CustomException(WarehouseErrorCode.MULTIPLE_REQUEST_WAREHOUSE_LOCATION);
            }
        }


        // DB 내부 중복 값 확인
        List<WarehouseLocation> existing = warehouseLocationRepository.findByCodeInIgnoreCase(codes);

        if (!existing.isEmpty()) {
            throw new CustomException(WarehouseErrorCode.MULTIPLE_WAREHOUSE_LOCATION);
        }

        List<WarehouseLocation> warehouseLocationList = dto.stream()
                .map(WarehouseLocationCreateRequestDto::toEntity)
                .peek(warehouseLocation -> warehouseLocation.updateWarehouse(warehouse))
                .toList();

        warehouseLocationRepository.saveAll(warehouseLocationList);

    }

    @Override
    public WarehouseResponseDto find(Long id) {
        Warehouse warehouse = valid(warehouseRepository.findById(id), WarehouseErrorCode.NOT_FOUND_WAREHOUSE);
        WarehouseResponseDto warehouseResponseDto = new WarehouseResponseDto(warehouse);
        warehouse.getWarehouseLocationList()
                .stream()
                .map(WarehouseLocationResponseDto::new)
                .forEach(warehouseResponseDto::addWarehouseLocationRequestDto);

        return warehouseResponseDto;
    }
}
