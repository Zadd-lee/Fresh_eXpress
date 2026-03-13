package com.mink.freshexpress.stock.model;

import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.stock.constant.StockHistoryType;
import com.mink.freshexpress.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class StockHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StockHistoryType type;
    @Column(nullable = false)
    private BigInteger quantity;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private User actor;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
