package com.warehouse.mapper;

import com.warehouse.dto.filter.PurchaseModelFilterDto;
import com.warehouse.model.PurchaseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class PurchaseModelMapper {
    public PurchaseModelFilterDto toDto(PurchaseModel purchaseModel) {
        return new PurchaseModelFilterDto(
                purchaseModel.getDate(),
                purchaseModel.getTotal()
        );
    }
}
