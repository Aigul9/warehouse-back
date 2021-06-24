package com.warehouse.dto.filter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Represents a purchase for filtering purposes.")
public class PurchaseModelFilterDto {

    private Date date;

    private Double total;

    public PurchaseModelFilterDto(Date date, Double total) {
        this.date = date;
        this.total = total;
    }
}
