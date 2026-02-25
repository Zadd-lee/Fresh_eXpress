package com.mink.freshexpress.product.service;

import com.mink.freshexpress.product.dto.CreateProductRequestDto;
import com.mink.freshexpress.product.dto.ProductResponseDto;
import com.mink.freshexpress.product.dto.UpdateProductRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    void create(@Valid CreateProductRequestDto dto);

    void createBulk(@Valid List<CreateProductRequestDto> dtoList);

    ProductResponseDto find(Long id);

    void delete(Long id);

    void update(Long id, UpdateProductRequestDto dto);
}
