package com.mink.freshexpress.order.repository;

import com.mink.freshexpress.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
