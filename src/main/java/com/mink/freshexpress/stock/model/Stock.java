package com.mink.freshexpress.stock.model;

import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.product.model.Product;
import com.mink.freshexpress.stock.constant.Status;
import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String lot;
    private BigInteger quantity;
    private LocalDate manufacturedAt;
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private WarehouseLocation location;

    public void updateManufacturedAt(LocalDate manufacturedAt) {
        this.manufacturedAt = manufacturedAt;
    }

    public void updateExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
