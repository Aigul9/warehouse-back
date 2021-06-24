package com.warehouse.dto.request;

import com.warehouse.model.ItemModel;
import com.warehouse.model.SupplyItemModel;
import com.warehouse.model.SupplyItemPK;
import com.warehouse.model.SupplyModel;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.SupplyModelService;
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
@ApiModel(description = "Represents an item row in a supply.")
public class RequestSupplyItemModelDto {

    @ApiModelProperty(value = "supply id")
    private Long supplyId;

    @ApiModelProperty(value = "item id")
    private Long itemId;

    @ApiModelProperty(value = "quantity of the item in the supply")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item in the supply")
    private Double price;

    public SupplyItemModel fromRequestSupplyItemModelDtoToSupplyItemModel(
            RequestSupplyItemModelDto requestSupplyItemModelDto,
            SupplyModelService supplyModelService,
            ItemModelService itemModelService) {

        SupplyItemModel supplyItemModel = new SupplyItemModel();

        supplyItemModel.setQuantity(requestSupplyItemModelDto.getQuantity());
        supplyItemModel.setPrice(requestSupplyItemModelDto.getPrice());

        SupplyModel supplyModel = supplyModelService.findById(requestSupplyItemModelDto.getSupplyId());
        ItemModel itemModel = itemModelService.findById(requestSupplyItemModelDto.getItemId());

        SupplyItemPK supplyItemPK = new SupplyItemPK();
        supplyItemPK.setSupply(supplyModel);
        supplyItemPK.setItem(itemModel);

        supplyItemModel.setId(supplyItemPK);

        return supplyItemModel;
    }
}