package com.warehouse.dto.filter;

import com.warehouse.model.InventoryStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents an inventory for filtering purposes.")
public class InventoryModelFilterDto {

    @ApiModelProperty(value = "date of the inventory")
    private Date date;

    @ApiModelProperty(value = "total sum of the inventory")
    private Double total;

    @ApiModelProperty(value = "status of the inventory")
    private InventoryStatus inventoryStatus;

    public InventoryModelFilterDto(Date date, Double total, InventoryStatus inventoryStatus) {
        this.date = date;
        this.total = total;
        this.inventoryStatus = inventoryStatus;
    }
}
