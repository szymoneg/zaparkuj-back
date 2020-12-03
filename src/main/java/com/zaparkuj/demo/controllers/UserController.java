package com.zaparkuj.demo.controllers;


import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.UserService;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    UserServiceImpl userService = new UserServiceImpl();

    @GetMapping("/regiser")
    public @ResponseBody String insertUser(){
        User newUser = new User();
        userService.insertUser(newUser);
        userRepository.save(newUser);

        return "created";
    }
}
