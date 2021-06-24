package com.warehouse.dto.filter;

import com.warehouse.model.InventoryStatus;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents an inventory for filtering purposes.")
public class InventoryModelFilterDto {

    private Date date;

    private Double total;

    private InventoryStatus inventoryStatus;

    public InventoryModelFilterDto(Date date, Double total, InventoryStatus inventoryStatus) {
        this.date = date;
        this.total = total;
        this.inventoryStatus = inventoryStatus;
    }
}
