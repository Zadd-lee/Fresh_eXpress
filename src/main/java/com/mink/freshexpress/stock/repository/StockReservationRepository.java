package com.mink.freshexpress.stock.repository;

import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.order.model.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockReservationRepository extends JpaRepository<StockReservation,Long> {
    List<StockReservation> findByOrder(Order order);
}
