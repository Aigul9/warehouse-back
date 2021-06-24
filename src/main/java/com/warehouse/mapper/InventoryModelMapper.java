package com.warehouse.mapper;

import com.warehouse.dto.filter.InventoryModelFilterDto;
import com.warehouse.model.InventoryModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class InventoryModelMapper {
    public InventoryModelFilterDto toDto(InventoryModel inventoryModel) {
        return new InventoryModelFilterDto(
                inventoryModel.getDate(),
                inventoryModel.getTotal(),
                inventoryModel.getInventoryStatus()
        );
    }
}
