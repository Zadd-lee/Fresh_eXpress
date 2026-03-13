package com.mink.freshexpress.order.model;

import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.product.constant.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal quantity;
    @Column(nullable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}
