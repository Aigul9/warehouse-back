package com.warehouse.repository;

import com.warehouse.model.PurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseModel, Long>, BaseRepository<PurchaseModel, Long> {
}
