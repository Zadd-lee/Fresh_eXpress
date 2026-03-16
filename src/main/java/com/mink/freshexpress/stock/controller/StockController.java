package com.mink.freshexpress.stock.controller;

import com.mink.freshexpress.stock.dto.CreateStockRequestDto;
import com.mink.freshexpress.stock.dto.StockResponseDto;
import com.mink.freshexpress.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetails userDetails,
                                       @Valid @RequestBody CreateStockRequestDto dto) {
        service.create(userDetails.getUsername(),dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> createBulk(@AuthenticationPrincipal UserDetails userDetails,
                                           @Valid @RequestBody List<CreateStockRequestDto> dtoList) {
        service.creatBulk(userDetails.getUsername(),dtoList);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDto> get(@PathVariable long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> discard(@PathVariable Long id) {
        service.discard(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
