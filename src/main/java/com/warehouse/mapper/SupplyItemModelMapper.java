package com.warehouse.mapper;

import com.warehouse.dto.filter.SupplyItemModelFilterDto;
import com.warehouse.model.SupplyItemModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class SupplyItemModelMapper {
    public SupplyItemModelFilterDto toDto(SupplyItemModel supplyItemModel) {
        return new SupplyItemModelFilterDto(
                supplyItemModel.getId().getSupply().getId(),
                supplyItemModel.getId().getItem().getId(),
                supplyItemModel.getQuantity(),
                supplyItemModel.getPrice()
        );
    }
}
