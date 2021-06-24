package com.warehouse.dto.request;

import com.warehouse.model.SupplyItemModel;
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
@ApiModel(description = "Represents an invoice.")
public class RequestInvoiceDto {

    @ApiModelProperty(value = "item id or item name")
    private String itemProperty;

    @ApiModelProperty(value = "category id or category name")
    private String categoryProperty;

    @ApiModelProperty(value = "quantity of the item in the invoice")
    private Integer quantity;

    @ApiModelProperty(value = "price of the item in the invoice")
    private Double price;

    public SupplyItemModel fromRequestInvoiceDtoToSupplyItemModel(RequestInvoiceDto requestInvoiceDto) {
        SupplyItemModel supplyItemModel = new SupplyItemModel();

        supplyItemModel.setQuantity(requestInvoiceDto.getQuantity());
        supplyItemModel.setPrice(requestInvoiceDto.getPrice());

        return supplyItemModel;
    }
}
