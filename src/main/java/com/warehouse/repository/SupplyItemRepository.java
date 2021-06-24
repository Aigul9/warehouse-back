package com.warehouse.repository;

import com.warehouse.model.SupplyItemModel;
import com.warehouse.model.SupplyItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItemModel, SupplyItemPK>,
        BaseRepository<SupplyItemModel, SupplyItemPK>{
}
