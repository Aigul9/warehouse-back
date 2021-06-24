package com.warehouse.service.impl;

import com.warehouse.model.UserModel;
import com.warehouse.repository.UserRepository;
import com.warehouse.roles.RolesPermissions;
import com.warehouse.service.UserModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.warehouse.roles.RolesPermissions.getPermissionsByRole;

@Service
@Transactional
@Slf4j
public class UserModelServiceImpl implements UserModelService, UserDetailsService {

    private final UserRepository userRepository;

    public UserModelServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            UserModel userModel = userRepository.findById(id).get();
            System.out.println(RolesPermissions.getAuthorities(userModel.getRole()));
            return new User(userModel.getUsername(), userModel.getPassword(),
                    RolesPermissions.getAuthorities(userModel.getRole()));
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Not found.");
        }
    }

    @Override
    public UserModel findById(String id) {
        try {
            return userRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, userId - %s", e.getMessage(), id));
            throw new NoSuchElementException("User is not found.");
        }
    }

    @Override
    public String deleteById(String id) {
        try {
            userRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, userId - %s", e.getMessage(), id));
            throw new NoSuchElementException("User is not found.");
        }
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public UserModel update(UserModel userModel, String id) {
        try {
            UserModel userModelById = userRepository.findById(id).get();
            userModelById.setUsername(userModel.getUsername());
            userModelById.setPassword(userModel.getPassword());
            userModelById.setRole(userModel.getRole());
            return userRepository.save(userModelById);
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, userId - %s", e.getMessage(), id));
            throw new NoSuchElementException("User is not found.");
        }
    }
}
