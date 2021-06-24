package com.warehouse.dto.request;

import com.warehouse.model.PurchaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Represents a purchase.")
public class RequestPurchaseModelDto {

    @ApiModelProperty(value = "date of the purchase")
    private Date date = new Date();

    @ApiModelProperty(value = "total sum of the purchase")
    private Double total;

    public PurchaseModel fromRequestPurchaseModelDtoToPurchaseModel(RequestPurchaseModelDto requestPurchaseModelDto) {
        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setDate(requestPurchaseModelDto.getDate());
        purchaseModel.setTotal(requestPurchaseModelDto.getTotal());
        return purchaseModel;
    }
}
