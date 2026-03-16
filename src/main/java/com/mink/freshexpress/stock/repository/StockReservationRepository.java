package com.mink.freshexpress.stock.repository;

import com.mink.freshexpress.order.model.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockReservationRepository extends JpaRepository<StockReservation,Long> {
}
