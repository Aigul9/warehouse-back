package com.warehouse.repository;

import com.warehouse.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long>, BaseRepository<ItemModel, Long> {
}