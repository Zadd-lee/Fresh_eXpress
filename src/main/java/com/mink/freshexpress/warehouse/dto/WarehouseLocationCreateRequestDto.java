package com.mink.freshexpress.warehouse.dto;

import com.mink.freshexpress.product.constant.Temperature;
import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WarehouseLocationCreateRequestDto {
    @NotBlank
    @Pattern(regexp = "^(R|C|F)-[A-Z](0[1-9]|[1-9][0-9])-(0[1-9]|[1-9][0-9])$")
    private String code;


    public WarehouseLocation toEntity() {
        return WarehouseLocation.builder()
                .code(code)
                .isActive(true)
                .temperature(Temperature.of(String.valueOf(code.charAt(0))))
                .build();
    }
}
