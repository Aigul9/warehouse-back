package com.warehouse.service;

import com.warehouse.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserModelService {

    UserDetails loadUserByUsername(String s);

    UserModel findById(String id);

    List<UserModel> findAll();

    String deleteById(String id);

    UserModel save(UserModel model);

    UserModel update(UserModel model, String id);
}
