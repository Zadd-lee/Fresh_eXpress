package com.mink.freshexpress.order.repository;

import com.mink.freshexpress.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
