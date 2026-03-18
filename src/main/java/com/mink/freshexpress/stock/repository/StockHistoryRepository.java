package com.mink.freshexpress.stock.repository;

import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.stock.model.Stock;
import com.mink.freshexpress.stock.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory,Long> {
    List<StockHistory> findByOrder(Order order);

    List<StockHistory> findByStock(Stock stock);
}
