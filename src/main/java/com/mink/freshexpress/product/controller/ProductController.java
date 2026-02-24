package com.mink.freshexpress.product.controller;

import com.mink.freshexpress.product.dto.CreateProductRequestDto;
import com.mink.freshexpress.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateProductRequestDto dto) {
        service.create(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/bulk")
    public  ResponseEntity<Void> bulkCreate(@Valid @RequestBody List<CreateProductRequestDto> dtoList) {
        service.createBulk(dtoList);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
