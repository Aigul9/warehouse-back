package com.warehouse.dto.request;

import com.warehouse.model.CategoryModel;
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
@ApiModel(description = "Represents a category of items.")
public class RequestCategoryModelDto {

    @Getter
    @ApiModelProperty(value = "name of the category")
    private String name;

    public CategoryModel fromRequestCategoryModelDtoToCategoryModel(RequestCategoryModelDto requestCategoryModelDto) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(requestCategoryModelDto.getName());
        return categoryModel;
    }
}
