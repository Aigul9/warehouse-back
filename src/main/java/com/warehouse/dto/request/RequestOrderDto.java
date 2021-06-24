package com.warehouse.dto.request;

import com.warehouse.model.PurchaseItemModel;
import com.warehouse.service.ItemModelService;
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
@ApiModel(description = "Represents an order.")
public class RequestOrderDto {

    @ApiModelProperty(value = "item id")
    private Long itemId;

    @ApiModelProperty(value = "quantity of the item in the order")
    private Integer quantity;

    public PurchaseItemModel fromRequestOrderDtoToPurchaseItemModel(RequestOrderDto requestOrderDto,
                                                                    ItemModelService itemModelService) {
        PurchaseItemModel purchaseItemModel = new PurchaseItemModel();

        purchaseItemModel.setQuantity(requestOrderDto.getQuantity());
        purchaseItemModel.setPrice(itemModelService.findById(requestOrderDto.getItemId()).getPrice());

        return purchaseItemModel;
    }
}
