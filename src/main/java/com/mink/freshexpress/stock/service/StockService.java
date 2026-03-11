package com.mink.freshexpress.stock.service;

import com.mink.freshexpress.stock.dto.CreateStockRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface StockService {
    void create(@Valid CreateStockRequestDto dto);

    void creatBulk(@Valid List<CreateStockRequestDto> dtoList);
}
