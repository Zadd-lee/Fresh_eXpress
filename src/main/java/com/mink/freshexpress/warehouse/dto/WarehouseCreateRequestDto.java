package com.mink.freshexpress.warehouse.dto;

import com.mink.freshexpress.warehouse.model.Warehouse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WarehouseCreateRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String address;

    public Warehouse toEntity() {
        return Warehouse.builder()
                .name(this.name)
                .address(this.address)
                .build();
    }

}
