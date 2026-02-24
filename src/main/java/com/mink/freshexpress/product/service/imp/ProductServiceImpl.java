package com.mink.freshexpress.product.service.imp;

import com.mink.freshexpress.category.model.Category;
import com.mink.freshexpress.category.repository.CategoryRepository;
import com.mink.freshexpress.common.util.Validator;
import com.mink.freshexpress.product.dto.CreateProductRequestDto;
import com.mink.freshexpress.product.model.Product;
import com.mink.freshexpress.product.repository.ProductRepository;
import com.mink.freshexpress.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mink.freshexpress.common.util.Validator.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void create(CreateProductRequestDto dto) {

        Product product = dto.toEntity();

        //category
        Category category = valid(categoryRepository.findById(Long.valueOf(dto.getCategoryId())));
        product.addCategory(category);

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void createBulk(List<CreateProductRequestDto> dtoList) {
        dtoList.stream()
                .map(dto->{
                    Product product = dto.toEntity();
                    Category category = valid(categoryRepository.findById(Long.valueOf(dto.getCategoryId())));
                    product.addCategory(category);
                    return product;
                })
                .forEach(productRepository::save);
    }
}
