package com.warehouse.mapper;

import com.warehouse.dto.filter.InventoryItemModelFilterDto;
import com.warehouse.model.InventoryItemModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class InventoryItemModelMapper {
    public InventoryItemModelFilterDto toDto(InventoryItemModel inventoryItemModel) {
        return new InventoryItemModelFilterDto(
                inventoryItemModel.getId().getInventory().getId(),
                inventoryItemModel.getId().getItem().getId(),
                inventoryItemModel.getQuantity(),
                inventoryItemModel.getPrice()
        );
    }
}
