package com.mink.freshexpress.category.controller;

import com.mink.freshexpress.category.dto.CreateCategoryRequestDto;
import com.mink.freshexpress.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateCategoryRequestDto dto) {
        service.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
