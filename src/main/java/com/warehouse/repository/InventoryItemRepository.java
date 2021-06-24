package com.warehouse.repository;

import com.warehouse.model.InventoryItemModel;
import com.warehouse.model.InventoryItemPK;
import com.warehouse.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItemModel, InventoryItemPK>,
    BaseRepository<InventoryItemModel, InventoryItemPK>{

    @Query(value = "select distinct item_id from inventory_item where inventory_id = ?1",
            nativeQuery = true)
    List<Long> getItemModelsByInventoryId(Long id);
}
