package com.mink.freshexpress.stock.service;

import com.mink.freshexpress.stock.dto.CreateStockRequestDto;
import com.mink.freshexpress.stock.dto.CreateStockReservationDto;
import com.mink.freshexpress.stock.dto.StockResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface StockService {
    void create(@Valid CreateStockRequestDto dto);

    void creatBulk(@Valid List<CreateStockRequestDto> dtoList);

    StockResponseDto get(long id);

    void discard(Long id);

    void createReservation(List<CreateStockReservationDto> dto);
}
