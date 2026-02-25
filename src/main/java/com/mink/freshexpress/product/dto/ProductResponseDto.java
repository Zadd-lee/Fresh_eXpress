package com.mink.freshexpress.product.dto;

import com.mink.freshexpress.category.dto.SimpleCategoryResponseDto;
import com.mink.freshexpress.category.model.Category;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CommonErrorCode;
import com.mink.freshexpress.product.constant.StorageTemp;
import com.mink.freshexpress.product.constant.Unit;
import com.mink.freshexpress.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductResponseDto {

    private final String code;
    private final String name;
    private final String storageTemp;
    private final String defaultShelfLifeDays;
    private final String unit;
    private final boolean isActive;

    private final List<SimpleCategoryResponseDto> categoryResponseDtoList = new ArrayList<>();

    public ProductResponseDto(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.storageTemp = product.getStorageTemp().toString();
        this.defaultShelfLifeDays = product.getDefaultShelfLifeDays().toString();
        this.unit = product.getUnit().toString();
        this.isActive = product.isActive();

        Category category = product.getCategory();
        int expectedDepth = category.getDepth();

        while (category != null) {
            categoryResponseDtoList.add(new SimpleCategoryResponseDto(category));
            if (category.getDepth() == 0) {
                break;
            }
            category = category.getParent();
        }

        if (categoryResponseDtoList.size() != expectedDepth + 1) {
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
