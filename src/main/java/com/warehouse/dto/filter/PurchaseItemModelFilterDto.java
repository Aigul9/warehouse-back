package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents a purchase item for filtering purposes.")
public class PurchaseItemModelFilterDto {

    @ApiModelProperty(value = "purchase id")
    private Long purchaseId;

    @ApiModelProperty(value = "item id")
    private Long itemId;

    @ApiModelProperty(value = "quantity of the item in the purchase")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item in the purchase")
    private Double price;

    public PurchaseItemModelFilterDto(
            Long purchaseId,
            Long itemId,
            Integer quantity,
            Double price
    ) {
        this.purchaseId = purchaseId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }
}
