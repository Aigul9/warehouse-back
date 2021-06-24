package com.warehouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
@ApiModel(description = "Represents an inventory.")
public class InventoryModel extends BaseEntity {

    @Id
    @Column
    @ApiModelProperty(value = "inventory id")
    private Long id;

    @Column(name = "date")
    @ApiModelProperty(value = "date of the inventory")
    private Date date;

    @Column(name = "total")
    @ApiModelProperty(value = "total sum of the inventory")
    private Double total;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "status of the inventory")
    private InventoryStatus inventoryStatus;
}

