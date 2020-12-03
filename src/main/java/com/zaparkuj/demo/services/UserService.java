package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.User;
import org.springframework.context.annotation.Configuration;


public interface UserService {
    User insertUser(User user);
}
