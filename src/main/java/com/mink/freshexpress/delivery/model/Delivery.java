package com.mink.freshexpress.delivery.model;

import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.delivery.constant.DeliveryStatus;
import com.mink.freshexpress.order.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDateTime assignedAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveryAt;

    @OneToOne
    private Order order;
}
