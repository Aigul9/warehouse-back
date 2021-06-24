package com.warehouse.repository;

import com.warehouse.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long>, BaseRepository<CategoryModel, Long> {
}
