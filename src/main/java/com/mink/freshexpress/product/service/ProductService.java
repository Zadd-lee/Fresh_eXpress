package com.mink.freshexpress.product.service;

import com.mink.freshexpress.product.dto.CreateProductRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    void create(@Valid CreateProductRequestDto dto);

    void createBulk(@Valid List<CreateProductRequestDto> dtoList);
}
