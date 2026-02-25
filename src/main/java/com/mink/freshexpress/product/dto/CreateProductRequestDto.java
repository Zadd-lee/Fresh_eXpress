package com.mink.freshexpress.product.dto;

import com.mink.freshexpress.product.constant.Temperature;
import com.mink.freshexpress.product.constant.Unit;
import com.mink.freshexpress.product.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateProductRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String code;
    @NotBlank
    private String storageTemp;

    @NotNull
    private String  defaultShelfLifeDays;

    @NotBlank
    private String categoryId;

    public Product toEntity() {
        return Product.builder()
                .code(this.code)
                .name(this.name)
                .unit(Unit.ofBySKUCode(this.code))
                .storageTemp(Temperature.of(this.storageTemp))
                .defaultShelfLifeDays(Integer.valueOf(this.defaultShelfLifeDays))
                .isActive(true)
                .build();
    }
}
