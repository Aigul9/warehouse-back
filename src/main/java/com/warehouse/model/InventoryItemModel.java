package com.warehouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory_item")
@ApiModel(description = "Represents an item row in an inventory.")
public class InventoryItemModel {

    @EmbeddedId
    @ApiModelProperty(value = "id of an item in the inventory")
    private InventoryItemPK id;

    @Column(name = "quantity")
    @ApiModelProperty(value = "quantity of the item in the inventory")
    private Integer quantity;

    @Column(name = "price")
    @ApiModelProperty(value = "price of the item in the inventory")
    private Double price;
}
