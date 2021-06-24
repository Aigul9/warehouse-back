package com.warehouse.dto.request;

import com.warehouse.model.SupplyModel;
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
@ApiModel(description = "Represents a supply.")
public class RequestSupplyModelDto {

    @ApiModelProperty(value = "date of the supply")
    private Date date = new Date();

    @ApiModelProperty(value = "total sum of the supply")
    private Double total;

    public SupplyModel fromRequestSupplyModelDtoToSupplyModel(RequestSupplyModelDto requestSupplyModelDto) {
        SupplyModel supplyModel = new SupplyModel();
        supplyModel.setDate(requestSupplyModelDto.getDate());
        supplyModel.setTotal(requestSupplyModelDto.getTotal());
        return supplyModel;
    }
}
