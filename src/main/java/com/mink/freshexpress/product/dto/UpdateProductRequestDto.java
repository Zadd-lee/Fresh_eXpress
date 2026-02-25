package com.mink.freshexpress.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateProductRequestDto {
    private String storageTemp;
    private String defaultShelfLifeDays;

}
