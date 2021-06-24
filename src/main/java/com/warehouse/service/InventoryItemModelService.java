package com.warehouse.service;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.InventoryItemModelFilterDto;
import com.warehouse.model.InventoryItemModel;
import com.warehouse.model.InventoryItemPK;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public interface InventoryItemModelService {
    InventoryItemModel findById(InventoryItemPK id);

    InventoryItemPK deleteById(InventoryItemPK id);

    InventoryItemModel save(InventoryItemModel model);

    InventoryItemModel update(InventoryItemModel model, InventoryItemPK id);

    PageDto<InventoryItemModelFilterDto> findAll(
            InventoryItemModelFilterDto inventoryItemModelFilterDto, int page, int size, Sort sort);
}
