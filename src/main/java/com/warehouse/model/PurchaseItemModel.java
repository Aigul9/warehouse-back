package com.warehouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_item")
@ApiModel(description = "Represents an item row in a purchase.")
public class PurchaseItemModel {

    @EmbeddedId
    @ApiModelProperty(value = "id of an item in the purchase")
    private PurchaseItemPK id;

    @Column(name = "quantity")
    @ApiModelProperty(value = "quantity of the item in the purchase")
    private Integer quantity;

    @Column(name = "price")
    @ApiModelProperty(value = "price of the item in the purchase")
    private Double price;
}

