package com.warehouse.dto.request;

import com.warehouse.model.InventoryModel;
import com.warehouse.service.InventoryModelService;
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
@ApiModel(description = "Represents an inventory approval.")
public class RequestInventoryApproveDto {

    @ApiModelProperty(value = "inventory id")
    Long inventoryId;

    @ApiModelProperty(value = "inventory status")
    boolean approved;

    public InventoryModel fromRequestInventoryApproveDtoToInventoryModel(
            RequestInventoryApproveDto requestInventoryApproveDto,
            InventoryModelService inventoryModelService) {
        InventoryModel inventoryModel = inventoryModelService.findById(requestInventoryApproveDto.getInventoryId());
        return inventoryModel;
    }
}
