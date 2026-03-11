package com.mink.freshexpress.stock.repository;

import com.mink.freshexpress.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
