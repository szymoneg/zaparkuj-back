package com.zaparkuj.demo.services;

import com.zaparkuj.demo.dto.EditUserRequest;
import com.zaparkuj.demo.dto.UserDTO;
import com.zaparkuj.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public interface UserService {
    UserDetails loadUserByUsername(String s);

    User save(UserDTO user);

    Optional<User> editUser(Long id, EditUserRequest editUserRequest);
}
