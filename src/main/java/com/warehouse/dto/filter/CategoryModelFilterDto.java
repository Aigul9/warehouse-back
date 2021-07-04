package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents an item category for filtering purposes.")
public class CategoryModelFilterDto {

    @ApiModelProperty(value = "name of the category")
    private String name;

    public CategoryModelFilterDto(String name) {
        this.name = name;
    }
}
