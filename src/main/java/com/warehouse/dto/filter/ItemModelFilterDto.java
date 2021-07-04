package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents an item for filtering purposes.")
public class ItemModelFilterDto {

    @ApiModelProperty(value = "name of the item")
    private String name;

    @ApiModelProperty(value = "available quantity of the item")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item")
    private Double price;

    @ApiModelProperty(value = "category id")
    private Long categoryId;

    public ItemModelFilterDto(
            String name,
            Integer quantity,
            Double price,
            Long categoryId
    ) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.categoryId = categoryId;
    }
}
