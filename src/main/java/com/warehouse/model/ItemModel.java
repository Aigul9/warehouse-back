package com.warehouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
@ApiModel(description = "Represents an item.")
public class ItemModel extends BaseEntity {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "item id")
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(value = "name of the item")
    private String name;

    @Column(name = "quantity")
    @ApiModelProperty(value = "available quantity of the item")
    private Integer quantity;

    @Column(name = "price")
    @ApiModelProperty(value = "price of the item")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel group;
}
