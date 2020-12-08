package com.zaparkuj.demo.controllers;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    public UserRepository userRepository;


    UserServiceImpl userService = new UserServiceImpl();

    @PostMapping("/register")
    public ResponseEntity<String> insertUserController(@RequestBody User user){
        return userService.insertUser(user,userRepository);
    }
}
