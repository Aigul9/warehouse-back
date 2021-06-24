package com.warehouse.dto.request;

import com.warehouse.model.CategoryModel;
import com.warehouse.model.ItemModel;
import com.warehouse.service.CategoryModelService;
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
@ApiModel(description = "Represents an item.")
public class RequestItemModelDto {

    @ApiModelProperty(value = "name of the item")
    private String name;

    @ApiModelProperty(value = "available quantity of the item")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item")
    private Double price;

    @ApiModelProperty(value = "category id")
    private Long categoryId;

    public ItemModel fromRequestItemModelDtoToItemModel(RequestItemModelDto requestItemModelDto,
                                                                  CategoryModelService categoryModelService) {
        ItemModel itemModel = new ItemModel();
        itemModel.setName(requestItemModelDto.getName());
        itemModel.setQuantity(requestItemModelDto.getQuantity());
        itemModel.setPrice(requestItemModelDto.getPrice());

        CategoryModel categoryModel = categoryModelService.findById(requestItemModelDto.getCategoryId());
        itemModel.setGroup(categoryModel);

        return itemModel;
    }
}