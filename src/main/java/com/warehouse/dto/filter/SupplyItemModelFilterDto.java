package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents a supply item for filtering purposes.")
public class SupplyItemModelFilterDto {

    @ApiModelProperty(value = "supply id")
    private Long supplyId;

    @ApiModelProperty(value = "item id")
    private Long itemId;

    @ApiModelProperty(value = "quantity of the item in the supply")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item in the supply")
    private Double price;

    public SupplyItemModelFilterDto(
            Long supplyId,
            Long itemId,
            Integer quantity,
            Double price
    ) {
        this.supplyId = supplyId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }
}
