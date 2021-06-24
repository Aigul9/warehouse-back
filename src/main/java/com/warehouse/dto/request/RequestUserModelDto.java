package com.warehouse.dto.request;

import com.warehouse.model.UserModel;
import com.warehouse.roles.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Represents a user.")
public class RequestUserModelDto {

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "user role")
    private Role role;

    public UserModel fromRequestUserModelDtoToUserModel(RequestUserModelDto requestUserModelDto,
                                                        PasswordEncoder bcryptEncoder) {
        UserModel userModel = new UserModel();
        userModel.setUsername(requestUserModelDto.getUsername());
        userModel.setPassword(bcryptEncoder.encode(requestUserModelDto.getPassword()));
        userModel.setRole(requestUserModelDto.getRole());
        return userModel;
    }
}
