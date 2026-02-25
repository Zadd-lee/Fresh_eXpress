package com.mink.freshexpress.product.model;

import com.mink.freshexpress.category.model.Category;
import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.product.constant.Unit;
import com.mink.freshexpress.product.constant.StorageTemp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StorageTemp storageTemp;

    @Column(nullable = false)
    private Integer defaultShelfLifeDays;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void addCategory(Category category) {
        this.category = category;
    }

    public void toggleActive() {
        this.isActive = !this.isActive;
    }
}
