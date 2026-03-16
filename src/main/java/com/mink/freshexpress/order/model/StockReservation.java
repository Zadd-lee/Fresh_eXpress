package com.mink.freshexpress.order.model;

import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.order.constant.ReservationStatus;
import com.mink.freshexpress.stock.model.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class StockReservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;


    public void updateOrder(Order order) {
        this.order = order;
    }

    public void updateStock(Stock stock) {
        this.stock = stock;
    }
}
