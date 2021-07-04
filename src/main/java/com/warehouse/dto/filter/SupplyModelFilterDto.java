package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents a supply for filtering purposes.")
public class SupplyModelFilterDto {

    @ApiModelProperty(value = "date of the supply")
    private Date date;

    @ApiModelProperty(value = "total sum of the supply")
    private Double total;

    public SupplyModelFilterDto(Date date, Double total) {
        this.date = date;
        this.total = total;
    }
}
