package com.warehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.roles.Role;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@ApiModel(description = "Represents a user.")
public class UserModel {

    @Id
    @Column(name = "username")
    @ApiModelProperty(value = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    @ApiModelProperty(value = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "user role")
    private Role role;
}
