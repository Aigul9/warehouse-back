package com.warehouse.repository;

import com.warehouse.model.PurchaseItemModel;
import com.warehouse.model.PurchaseItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItemModel, PurchaseItemPK>,
    BaseRepository<PurchaseItemModel, PurchaseItemPK>{
}
