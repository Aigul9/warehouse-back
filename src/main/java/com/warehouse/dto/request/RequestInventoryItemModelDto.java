package com.warehouse.dto.request;

import com.warehouse.model.ItemModel;
import com.warehouse.model.InventoryItemModel;
import com.warehouse.model.InventoryItemPK;
import com.warehouse.model.InventoryModel;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.InventoryModelService;
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
@ApiModel(description = "Represents an item row in an inventory.")
public class RequestInventoryItemModelDto {

    @ApiModelProperty(value = "inventory id")
    private Long inventoryId;

    @ApiModelProperty(value = "item id")
    private Long itemId;

    @ApiModelProperty(value = "quantity of the item in the inventory")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item in the inventory")
    private Double price;

    public InventoryItemModel fromRequestInventoryItemModelDtoToInventoryItemModel(
            RequestInventoryItemModelDto requestInventoryItemModelDto,
            InventoryModelService inventoryModelService,
            ItemModelService itemModelService) {

        InventoryItemModel inventoryItemModel = new InventoryItemModel();

        inventoryItemModel.setQuantity(requestInventoryItemModelDto.getQuantity());
        inventoryItemModel.setPrice(requestInventoryItemModelDto.getPrice());

        InventoryModel inventoryModel = inventoryModelService.findById(requestInventoryItemModelDto.getInventoryId());
        ItemModel itemModel = itemModelService.findById(requestInventoryItemModelDto.getItemId());

        InventoryItemPK InventoryItemPK = new InventoryItemPK();
        InventoryItemPK.setInventory(inventoryModel);
        InventoryItemPK.setItem(itemModel);

        inventoryItemModel.setId(InventoryItemPK);

        return inventoryItemModel;
    }
}
