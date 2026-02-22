package com.mink.freshexpress.category.dto;

import com.mink.freshexpress.category.model.Category;
import lombok.Getter;

@Getter
public class SimpleCategoryResponseDto {
    private final Long id;
    private final String name;
    private final Long depth;

    public SimpleCategoryResponseDto(Category category) {
        this.name = category.getName();
        this.id = category.getId();
        this.depth = category.getDepth();
    }
}
