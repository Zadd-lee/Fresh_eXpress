package com.mink.freshexpress.category.repository;

import com.mink.freshexpress.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByNameLikeIgnoreCase(String name);

    List<Category> findByName(String name);

    boolean existsByName(String name);
}
