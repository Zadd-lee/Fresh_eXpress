package com.mink.freshexpress.category.service;

import com.mink.freshexpress.category.dto.CategoryResponseDto;
import com.mink.freshexpress.category.dto.CreateCategoryRequestDto;
import com.mink.freshexpress.category.dto.SearchCategoryRequestDto;
import com.mink.freshexpress.category.dto.SimpleCategoryResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {

    void create(@Valid CreateCategoryRequestDto dto);

    CategoryResponseDto find(Long id);

    List<SimpleCategoryResponseDto> search(SearchCategoryRequestDto dto);
}
