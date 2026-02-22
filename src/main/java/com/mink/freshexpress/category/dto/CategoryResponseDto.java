package com.mink.freshexpress.category.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class CategoryResponseDto {
    private final String name;
    private final List<SimpleCategoryResponseDto> parentsList = new ArrayList<>();
    private final List<SimpleCategoryResponseDto> childrenList = new ArrayList<>();

    public CategoryResponseDto(String name) {
        this.name = name;
    }

    public void addChild(SimpleCategoryResponseDto childDto) {
        this.childrenList.add(childDto);
    }

    public void addParent(SimpleCategoryResponseDto parentDto) {
        this.parentsList.add(parentDto);
    }
}
