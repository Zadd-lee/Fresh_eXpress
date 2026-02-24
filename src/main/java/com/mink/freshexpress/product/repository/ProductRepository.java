package com.mink.freshexpress.product.repository;

import com.mink.freshexpress.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
