package com.mink.freshexpress.category.service;

import com.mink.freshexpress.category.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {

    void create(@Valid CreateCategoryRequestDto dto);

    CategoryResponseDto find(Long id);

    List<SimpleCategoryResponseDto> search(SearchCategoryRequestDto dto);

    void delete(Long id);

    void update(Long id, UpdateCategoryRequestDto dto);
}
