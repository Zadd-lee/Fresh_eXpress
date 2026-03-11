package com.mink.freshexpress.warehouse.model;

import com.mink.freshexpress.product.constant.Temperature;
import com.mink.freshexpress.stock.model.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class WarehouseLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Temperature temperature;
    private String code;
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "location")
    private List<Stock> stockList = new ArrayList<>();


    public void updateWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void delete() {
        this.isActive = false;
    }
}
