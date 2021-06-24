package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents a purchase item for filtering purposes.")
public class PurchaseItemModelFilterDto {

    private Long purchaseId;
    private Long itemId;
    private Integer quantity;
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
