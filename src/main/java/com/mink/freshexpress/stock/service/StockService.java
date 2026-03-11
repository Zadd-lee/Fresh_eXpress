package com.mink.freshexpress.stock.service;

import com.mink.freshexpress.stock.dto.CreateStockRequestDto;
import jakarta.validation.Valid;

public interface StockService {
    void create(@Valid CreateStockRequestDto dto);
}
