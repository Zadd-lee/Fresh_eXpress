package com.mink.freshexpress.delivery.repository;

import com.mink.freshexpress.delivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
