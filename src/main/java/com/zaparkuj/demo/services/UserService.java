package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<String> insertUser(User user, UserRepository userRepository);
}
