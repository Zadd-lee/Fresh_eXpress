package com.mink.freshexpress.category.controller;

import com.mink.freshexpress.category.dto.CategoryResponseDto;
import com.mink.freshexpress.category.dto.CreateCategoryRequestDto;
import com.mink.freshexpress.category.dto.SearchCategoryRequestDto;
import com.mink.freshexpress.category.dto.SimpleCategoryResponseDto;
import com.mink.freshexpress.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SimpleCategoryResponseDto>> search(@Valid @RequestBody SearchCategoryRequestDto dto) {
        return new ResponseEntity<>(service.search(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
