package com.mink.freshexpress.warehouse.controller;

import com.mink.freshexpress.warehouse.dto.WarehouseCreateRequestDto;
import com.mink.freshexpress.warehouse.dto.WarehouseLocationCreateRequestDto;
import com.mink.freshexpress.warehouse.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody WarehouseCreateRequestDto dto) {
        service.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createLocation(@PathVariable Long id, @Valid @RequestBody List<WarehouseLocationCreateRequestDto> dto) {
        service.createLocation(id, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
