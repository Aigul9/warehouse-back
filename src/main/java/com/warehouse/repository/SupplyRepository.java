package com.warehouse.repository;

import com.warehouse.model.SupplyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<SupplyModel, Long>, BaseRepository<SupplyModel, Long> {
}
