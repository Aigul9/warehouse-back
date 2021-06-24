package com.warehouse.mapper;

import com.warehouse.dto.filter.SupplyModelFilterDto;
import com.warehouse.model.SupplyModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class SupplyModelMapper {
    public SupplyModelFilterDto toDto(SupplyModel supplyModel) {
        return new SupplyModelFilterDto(
                supplyModel.getDate(),
                supplyModel.getTotal()
        );
    }
}
