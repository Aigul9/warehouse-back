package com.warehouse.mapper;

import com.warehouse.dto.filter.PurchaseItemModelFilterDto;
import com.warehouse.model.PurchaseItemModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class PurchaseItemModelMapper {
    public PurchaseItemModelFilterDto toDto(PurchaseItemModel purchaseItemModel) {
        return new PurchaseItemModelFilterDto(
                purchaseItemModel.getId().getPurchase().getId(),
                purchaseItemModel.getId().getItem().getId(),
                purchaseItemModel.getQuantity(),
                purchaseItemModel.getPrice()
        );
    }
}
