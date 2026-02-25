package com.mink.freshexpress.product.service.imp;

import com.mink.freshexpress.category.model.Category;
import com.mink.freshexpress.category.repository.CategoryRepository;
import com.mink.freshexpress.common.exception.constant.CategoryErrorCode;
import com.mink.freshexpress.common.exception.constant.ProductErrorCode;
import com.mink.freshexpress.product.dto.CreateProductRequestDto;
import com.mink.freshexpress.product.dto.ProductResponseDto;
import com.mink.freshexpress.product.dto.UpdateProductRequestDto;
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
        Category category = valid(categoryRepository.findById(Long.valueOf(dto.getCategoryId())), CategoryErrorCode.NOT_FOUND);
        product.addCategory(category);

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void createBulk(List<CreateProductRequestDto> dtoList) {
        dtoList.stream()
                .map(dto->{
                    Product product = dto.toEntity();
                    Category category = valid(categoryRepository.findById(Long.valueOf(dto.getCategoryId())),CategoryErrorCode.NOT_FOUND);
                    product.addCategory(category);
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductResponseDto find(Long id){
        Product product = valid(productRepository.findById(id), ProductErrorCode.NOT_FOUND);
        return new ProductResponseDto(product);
    }

    @Override
    public void delete(Long id) {
        //valid
        Product product = valid(productRepository.findById(id), ProductErrorCode.NOT_FOUND);
        product.toggleActive();
    }

    @Transactional
    @Override
    public void update(Long id, UpdateProductRequestDto dto) {
        Product product = valid(productRepository.findById(id),ProductErrorCode.NOT_FOUND);

        if(dto.getDefaultShelfLifeDays() != null && !dto.getDefaultShelfLifeDays().isBlank()) product.updateDefaultShelfLifeDays(dto.getDefaultShelfLifeDays());
        if(dto.getStorageTemp() != null && !dto.getStorageTemp().isBlank()) product.updateStorageTemp(dto.getStorageTemp());

    }
}
