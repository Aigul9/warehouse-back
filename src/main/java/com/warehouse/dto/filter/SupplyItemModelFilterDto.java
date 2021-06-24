package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents a supply item for filtering purposes.")
public class SupplyItemModelFilterDto {

    private Long supplyId;
    private Long itemId;
    private Integer quantity;
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
