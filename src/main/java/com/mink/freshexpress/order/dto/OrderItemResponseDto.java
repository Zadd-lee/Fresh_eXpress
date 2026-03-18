package com.mink.freshexpress.order.dto;

import com.mink.freshexpress.order.model.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponseDto {
    private final String id;
    private final String quantity;
    private final String unit;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.id = String.valueOf(orderItem.getId());
        this.quantity = orderItem.getQuantity().toString();
        this.unit = String.valueOf(orderItem.getUnit());
    }
}
