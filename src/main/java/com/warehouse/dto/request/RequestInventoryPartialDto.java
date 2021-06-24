package com.warehouse.dto.request;

import com.warehouse.model.InventoryItemModel;
import com.warehouse.service.ItemModelService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Represents an inventory.")
public class RequestInventoryPartialDto {

    @ApiModelProperty(value = "item id")
    Long id;

    @ApiModelProperty(value = "quantity of the item in the inventory")
    private Integer quantity;

    public InventoryItemModel fromRequestInventoryPartialDtoToItemModel(RequestInventoryPartialDto requestInventoryPartialDto,
                                                                        ItemModelService itemModelService) {
        InventoryItemModel inventoryItemModel = new InventoryItemModel();
        inventoryItemModel.setQuantity(requestInventoryPartialDto.getQuantity());
        inventoryItemModel.setPrice(itemModelService.findById(requestInventoryPartialDto.getId()).getPrice());
        return inventoryItemModel;
    }
}
