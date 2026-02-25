package com.mink.freshexpress.warehouse.repository;

import com.mink.freshexpress.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}
