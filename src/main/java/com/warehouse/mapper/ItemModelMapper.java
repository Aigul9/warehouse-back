package com.warehouse.mapper;

import com.warehouse.dto.filter.ItemModelFilterDto;
import com.warehouse.model.ItemModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class ItemModelMapper {
    public ItemModelFilterDto toDto(ItemModel itemModel) {
        return new ItemModelFilterDto(
                itemModel.getName(),
                itemModel.getQuantity(),
                itemModel.getPrice(),
                itemModel.getGroup().getId());
    }
}
