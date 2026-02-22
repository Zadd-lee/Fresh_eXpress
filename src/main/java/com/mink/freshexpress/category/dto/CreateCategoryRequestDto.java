package com.mink.freshexpress.category.dto;

import com.mink.freshexpress.category.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCategoryRequestDto {
    @NotBlank
     String name;

     String parentCategoryName;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .enable(true)
                .build();
    }
}
