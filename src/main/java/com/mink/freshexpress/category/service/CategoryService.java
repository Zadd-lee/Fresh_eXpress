package com.mink.freshexpress.category.service;

import com.mink.freshexpress.category.dto.CategoryResponseDto;
import com.mink.freshexpress.category.dto.CreateCategoryRequestDto;
import jakarta.validation.Valid;

public interface CategoryService {

    void create(@Valid CreateCategoryRequestDto dto);

    CategoryResponseDto find(Long id);
}
