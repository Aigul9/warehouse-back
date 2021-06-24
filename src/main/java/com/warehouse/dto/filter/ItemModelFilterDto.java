package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents an item for filtering purposes.")
public class ItemModelFilterDto {

    private String name;
    private Integer quantity;
    private Double price;
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
