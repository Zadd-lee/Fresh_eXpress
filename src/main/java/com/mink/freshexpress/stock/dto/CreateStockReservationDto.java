package com.mink.freshexpress.stock.dto;

import com.mink.freshexpress.order.constant.ReservationStatus;
import com.mink.freshexpress.order.model.StockReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CreateStockReservationDto {
    private final Integer quantity;
    private final Long orderItemId;
    private final Long stockId;
    public StockReservation toEntity() {
        return StockReservation.builder()
                .quantity(BigDecimal.valueOf(this.quantity))
                .status(ReservationStatus.RESERVED)
                .build();
    }
}
