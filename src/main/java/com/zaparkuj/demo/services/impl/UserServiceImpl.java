package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User insertUser(User user) {
        user.setUsername("kornelkornel");
        user.setPassword("123");
        user.setEmail("kornel@mail.com");

        return user;
    }
}
