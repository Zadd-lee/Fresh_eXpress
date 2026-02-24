package com.mink.freshexpress.product.service;

import com.mink.freshexpress.product.dto.CreateProductRequestDto;
import jakarta.validation.Valid;

public interface ProductService {
    void create(@Valid CreateProductRequestDto dto);
}
