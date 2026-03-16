package com.mink.freshexpress.order.service;

import com.mink.freshexpress.order.dto.CreateOrderRequestDto;
import jakarta.validation.Valid;

public interface OrderService {
    void create(String username, @Valid CreateOrderRequestDto dto);
}
