package com.mink.freshexpress.warehouse.repository;

import com.mink.freshexpress.warehouse.model.WarehouseLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface WarehouseLocationRepository extends JpaRepository<WarehouseLocation,Long> {

    List<WarehouseLocation> findByCodeIgnoreCase(String code);

    List<WarehouseLocation> findByCodeInIgnoreCase(Set<String> attr0);

    List<WarehouseLocation> code(String code);
}
