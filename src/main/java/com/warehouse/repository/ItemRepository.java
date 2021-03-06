package com.warehouse.repository;

import com.warehouse.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long>, BaseRepository<ItemModel, Long> {
    @Query(value = "select distinct id from item where category_id = ?1",
            nativeQuery = true)
    List<Long> getItemsByCategoryId(Long id);
}
