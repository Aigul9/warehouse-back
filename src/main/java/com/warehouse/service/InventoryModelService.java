package com.warehouse.service;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.InventoryModelFilterDto;
import com.warehouse.model.InventoryModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public interface InventoryModelService {
    InventoryModel findById(Long id);

    Long deleteById(Long id);

    InventoryModel save(InventoryModel model);

    InventoryModel update(InventoryModel model, long id);

    boolean checkTotal(double total);

    PageDto<InventoryModelFilterDto> findAll(
            InventoryModelFilterDto inventoryModelFilterDto, int page, int size, Sort sort);
}
