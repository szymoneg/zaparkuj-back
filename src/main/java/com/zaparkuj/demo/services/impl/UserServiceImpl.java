package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User insertUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());

        return user;
    }
}
