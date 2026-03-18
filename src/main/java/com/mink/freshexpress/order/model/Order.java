package com.mink.freshexpress.order.model;

import com.mink.freshexpress.common.model.BaseEntity;
import com.mink.freshexpress.order.constant.OrderStatus;
import com.mink.freshexpress.stock.model.StockHistory;
import com.mink.freshexpress.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNo;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private String recipientName;
    @Column(nullable = false)
    private String recipientPhone;
    @Column(nullable = false)
    private String address;
    private String deliveryNote;

    private LocalDate scheduledDeliveryDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<StockReservation> stockReservationList = new ArrayList<>();


    public void updateRecipientName(String name) {
        this.recipientName = name;
    }

    public void updateCustomer(User user) {
        this.customer = user;
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
