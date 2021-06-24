package com.warehouse.dto.request;

import com.warehouse.model.InventoryModel;
import com.warehouse.model.InventoryStatus;
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
@ApiModel(description = "Represents an inventory.")
public class RequestInventoryModelDto {

    @ApiModelProperty(value = "date of the inventory")
    private Date date = new Date();

    @ApiModelProperty(value = "total sum of the inventory")
    private Double total;

    public InventoryModel fromRequestInventoryModelDtoToInventoryModel(RequestInventoryModelDto requestInventoryModelDto) {
        InventoryModel inventoryModel = new InventoryModel();
        inventoryModel.setDate(requestInventoryModelDto.getDate());
        inventoryModel.setTotal(requestInventoryModelDto.getTotal());
        inventoryModel.setInventoryStatus(InventoryStatus.PENDING);
        return inventoryModel;
    }
}
