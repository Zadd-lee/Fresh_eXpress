package com.mink.freshexpress.warehouse.model;

import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.stock.model.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Warehouse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "warehouse")
    private List<WarehouseLocation> warehouseLocationList = new ArrayList<>();

    public void delete() {
        this.isActive = false;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public List<Stock> getAllStockList() {
        return warehouseLocationList.stream()
                .flatMap(l -> l.getStockList().stream())
                .toList();
    }
}
