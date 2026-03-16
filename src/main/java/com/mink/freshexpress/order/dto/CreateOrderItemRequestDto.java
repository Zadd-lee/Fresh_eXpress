package com.mink.freshexpress.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CreateOrderItemRequestDto {
    private Long productId;
    private Integer quantity;

}
