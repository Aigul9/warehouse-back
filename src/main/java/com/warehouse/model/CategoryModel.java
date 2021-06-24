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


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
@ApiModel(description = "Represents a category of items.")
public class CategoryModel extends BaseEntity {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "category id")
    private Long id;

    @Getter
    @Column(name = "name")
    @ApiModelProperty(value = "name of the category")
    private String name;
}
