package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents an inventory item for filtering purposes.")
public class InventoryItemModelFilterDto {

    private Long inventoryId;
    private Long itemId;
    private Integer quantity;
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
