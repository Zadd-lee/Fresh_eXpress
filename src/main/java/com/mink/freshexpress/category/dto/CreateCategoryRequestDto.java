package com.mink.freshexpress.category.dto;

import com.mink.freshexpress.category.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCategoryRequestDto {
    @NotBlank
     String name;

     String parentCategoryId;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .enable(true)
                .build();
    }
}
