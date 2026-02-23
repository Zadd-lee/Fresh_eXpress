package com.mink.freshexpress.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchCategoryRequestDto {
    @NotBlank
    private String name;

    private String isEnable;
}
