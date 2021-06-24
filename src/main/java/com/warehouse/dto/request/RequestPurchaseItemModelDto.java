package com.warehouse.dto.request;

import com.warehouse.model.ItemModel;
import com.warehouse.model.PurchaseItemModel;
import com.warehouse.model.PurchaseItemPK;
import com.warehouse.model.PurchaseModel;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.PurchaseModelService;
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
@ApiModel(description = "Represents an item row in a purchase.")
public class RequestPurchaseItemModelDto {

    @ApiModelProperty(value = "purchase id")
    private Long purchaseId;

    @ApiModelProperty(value = "item id")
    private Long itemId;

    @ApiModelProperty(value = "quantity of the item in the purchase")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item in the purchase")
    private Double price;

    public PurchaseItemModel fromRequestPurchaseItemModelDtoToPurchaseItemModel(
            RequestPurchaseItemModelDto requestPurchaseItemModelDto,
            PurchaseModelService purchaseModelService,
            ItemModelService itemModelService) {

        PurchaseItemModel purchaseItemModel = new PurchaseItemModel();

        purchaseItemModel.setQuantity(requestPurchaseItemModelDto.getQuantity());
        purchaseItemModel.setPrice(requestPurchaseItemModelDto.getPrice());

        PurchaseModel purchaseModel = purchaseModelService.findById(requestPurchaseItemModelDto.getPurchaseId());
        ItemModel itemModel = itemModelService.findById(requestPurchaseItemModelDto.getItemId());

        PurchaseItemPK purchaseItemPK = new PurchaseItemPK();
        purchaseItemPK.setPurchase(purchaseModel);
        purchaseItemPK.setItem(itemModel);

        purchaseItemModel.setId(purchaseItemPK);

        return purchaseItemModel;
    }
}