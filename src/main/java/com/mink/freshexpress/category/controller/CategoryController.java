package com.mink.freshexpress.category.controller;

import com.mink.freshexpress.category.dto.CategoryResponseDto;
import com.mink.freshexpress.category.dto.CreateCategoryRequestDto;
import com.mink.freshexpress.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> find(@PathVariable Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);

    }

}
