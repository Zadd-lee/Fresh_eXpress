package com.mink.freshexpress.stock.repository;

import com.mink.freshexpress.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findAllByExpiredAtAfter(LocalDateTime now);
}
