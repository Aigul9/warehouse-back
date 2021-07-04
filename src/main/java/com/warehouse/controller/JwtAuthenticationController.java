package com.warehouse.controller;

import com.warehouse.config.jwt.JwtTokenUtil;
import com.warehouse.dto.request.RequestDefaultUserModelDto;
import com.warehouse.dto.request.RequestUserModelDto;
import com.warehouse.dto.request.JwtRequest;
import com.warehouse.dto.request.JwtResponse;
import com.warehouse.model.UserModel;
import com.warehouse.service.UserModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class JwtAuthenticationController {

    private final UserModelService userModelService;

    public AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final PasswordEncoder bcryptEncoder;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    
    @PostMapping("/authenticate")
    @ApiOperation(value = "Create token", notes = "This method is used to create authentication token.")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userModelService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = "Bearer " + jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @PostMapping("/api/v1/users")
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @ApiOperation(value = "Add user", notes = "This method is used to add a new user.")
    public ResponseEntity<UserModel> post(@RequestBody RequestUserModelDto requestUserModelDto) {
        return new ResponseEntity<>(userModelService.save(
                requestUserModelDto.fromRequestUserModelDtoToUserModel(requestUserModelDto,
                        bcryptEncoder)),
                HttpStatus.OK);
    }

    @PostMapping("/register")
    @ApiOperation(value = "Register", notes = "This method is used to add a new user.")
    public ResponseEntity<UserModel> saveUser(@RequestBody RequestDefaultUserModelDto requestUserModelDto) {
        return new ResponseEntity<>(userModelService.save(
                requestUserModelDto.fromRequestUserModelDtoToUserModel(requestUserModelDto,
                        bcryptEncoder)),
                HttpStatus.OK);
    }

    @GetMapping("/api/v1/users")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get users", notes = "This method is used to get list of users.")
    public List<UserModel> getAllUser() {
        return userModelService.findAll();
    }

    @GetMapping("/api/v1/users/{id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get user", notes = "This method is used to get specific user.")
    public UserModel get(@PathVariable("id") @ApiParam(value = "User id") String id) {
        return userModelService.findById(id);
    }

    @DeleteMapping("/api/v1/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @ApiOperation(value = "Delete user", notes = "This method is used to delete the user.")
    public String delete(@PathVariable("id") @ApiParam(value = "User id") String id) {
        return userModelService.deleteById(id);
    }

    @PutMapping("/api/v1/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @ApiOperation(value = "Update user info", notes = "This method is used to update the user info.")
    public UserModel update(@RequestBody RequestUserModelDto requestUserModelDto,
                                @PathVariable("id") @ApiParam(value = "User id") String id) {
        return userModelService.update(
                requestUserModelDto.fromRequestUserModelDtoToUserModel(requestUserModelDto, bcryptEncoder), id);
    }
}
