package com.mink.freshexpress.stock.dto;

import com.mink.freshexpress.stock.model.Stock;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.text.DateFormatter;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateStockRequestDto {

    @NotBlank
    private String lot;

    @Positive
    private BigInteger quantity;

    private String manufacturedAt;

    private String expiredAt;

    @Positive
    private long productId;

    @Positive
    private long locationId;

    public Stock toEntity() {
        return Stock.builder()
                .lot(this.lot)
                .initialQuantity(this.quantity)
                .currentQuantity(this.quantity)
                .build();
    }
}



