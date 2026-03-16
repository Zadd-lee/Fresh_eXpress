package com.mink.freshexpress.order.service;

import com.mink.freshexpress.order.dto.CreateOrderRequestDto;
import com.mink.freshexpress.stock.dto.CreateStockReservationDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    List<CreateStockReservationDto> create(String username, @Valid CreateOrderRequestDto dto);
}
