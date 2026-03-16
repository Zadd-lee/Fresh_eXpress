package com.mink.freshexpress.stock.repository;

import com.mink.freshexpress.stock.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory,Long> {
}
