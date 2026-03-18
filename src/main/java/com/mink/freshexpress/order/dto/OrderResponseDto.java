package com.mink.freshexpress.order.dto;

import com.mink.freshexpress.order.constant.OrderStatus;
import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.order.model.OrderItem;
import com.mink.freshexpress.user.model.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {
    private final String id;

    private final String orderNo;

    private final String status;

    private final String recipientName;

    private final String recipientPhone;

    private final String address;

    private final String deliveryNote;

    private final String scheduledDeliveryDate;

    private final String customerId;
    private final String customerName;

    private final List<OrderItemResponseDto> orderItemResponseDtoList = new ArrayList<>();

    public OrderResponseDto(Order order) {
        this.id = order.getId().toString();
        this.orderNo = order.getOrderNo();
        this.status = order.getStatus().name();
        this.recipientName = order.getRecipientName();
        this.recipientPhone = order.getRecipientPhone();
        this.address = order.getAddress();
        this.deliveryNote = order.getDeliveryNote();
        if (order.getScheduledDeliveryDate() != null) {
            this.scheduledDeliveryDate = order.getScheduledDeliveryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-DD"));
        } else {
            this.scheduledDeliveryDate = null;
        }

        User customer = order.getCustomer();
        this.customerId = String.valueOf(customer.getId());
        this.customerName = customer.getName();

        order.getOrderItemList()
                .stream()
                .map(OrderItemResponseDto::new)
                .forEach(orderItemResponseDtoList::add);
    }
}
