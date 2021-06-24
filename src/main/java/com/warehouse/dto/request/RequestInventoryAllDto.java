package com.warehouse.dto.request;

import com.warehouse.model.ItemModel;
import com.warehouse.service.ItemModelService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Represents an inventory.")
public class RequestInventoryAllDto {

    @ApiModelProperty(value = "list of items")
    List<Long> itemIds;

    public List<ItemModel> fromRequestInventoryAllDtoToItemModelList(RequestInventoryAllDto requestInventoryAllDto,
                                                                  ItemModelService itemModelService) {
        List<ItemModel> itemModels = new ArrayList<>();

        for (Long id: requestInventoryAllDto.getItemIds()) {
            itemModels.add(itemModelService.findById(id));
        }

        return itemModels;
    }
}
