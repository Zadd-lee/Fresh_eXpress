package com.mink.freshexpress.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WarehouseUpdateRequestDto {
    private String name;
    private String address;
    
}
