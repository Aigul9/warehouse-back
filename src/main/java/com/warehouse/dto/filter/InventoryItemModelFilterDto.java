package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents an inventory item for filtering purposes.")
public class InventoryItemModelFilterDto {

    @ApiModelProperty(value = "inventory id")
    private Long inventoryId;

    @ApiModelProperty(value = "item id")
    private Long itemId;

    @ApiModelProperty(value = "quantity of the item in the inventory")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item in the inventory")
    private Double price;
    
    public InventoryItemModelFilterDto(
            Long inventoryId,
            Long itemId,
            Integer quantity,
            Double price  
    ) {
        this.inventoryId = inventoryId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }
}
