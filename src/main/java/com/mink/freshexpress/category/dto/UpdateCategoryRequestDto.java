package com.mink.freshexpress.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequestDto {
    private String newParentId;
    private Boolean isRestore;
}
