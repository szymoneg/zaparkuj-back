package com.zaparkuj.demo.services;

import com.zaparkuj.demo.dto.UserRequest;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import org.springframework.http.ResponseEntity;


public interface UserService {
    boolean insertUser(User user);
    boolean loginUser(UserRequest userRequest);
}
