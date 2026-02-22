package com.mink.freshexpress.category.repository;

import com.mink.freshexpress.category.model.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByNameContains(String name);


    List<Category> findByName(String name);
}
