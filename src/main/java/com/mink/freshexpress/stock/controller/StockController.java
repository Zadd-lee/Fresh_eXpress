package com.mink.freshexpress.stock.controller;

import com.mink.freshexpress.stock.dto.CreateStockRequestDto;
import com.mink.freshexpress.stock.dto.StockResponseDto;
import com.mink.freshexpress.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @PostMapping
    public ResponseEntity<Void> post(@Valid @RequestBody CreateStockRequestDto dto) {
        service.create(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> createBulk(@Valid @RequestBody List<CreateStockRequestDto> dtoList) {
        service.creatBulk(dtoList);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDto> get(@PathVariable long id) {
        return new ResponseEntity<>(service.get(id),HttpStatus.OK);
    }
}
