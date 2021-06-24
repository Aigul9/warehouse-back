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
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase")
@ApiModel(description = "Represents a purchase.")
public class PurchaseModel extends BaseEntity {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "purchase id")
    private Long id;

    @Column(name = "date")
    @ApiModelProperty(value = "date of the purchase")
    private Date date;

    @Column(name = "total")
    @ApiModelProperty(value = "total sum of the purchase")
    private Double total;
}
