package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents a supply for filtering purposes.")
public class SupplyModelFilterDto {

    private Date date;

    private Double total;

    public SupplyModelFilterDto(Date date, Double total) {
        this.date = date;
        this.total = total;
    }
}
