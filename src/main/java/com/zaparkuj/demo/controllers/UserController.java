package com.zaparkuj.demo.controllers;


import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.UserService;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    UserServiceImpl userService = new UserServiceImpl();

//    @GetMapping("/regiser")
//    public @ResponseBody String insertUser(){
//        User newUser = new User();
//        userService.insertUser(newUser);
//        userRepository.save(newUser);
//
//        return "created";
//    }

    @PostMapping("/register")
    public ResponseEntity<Void> insertUser(@RequestBody User newUser){
        userService.insertUser(newUser);
        userRepository.save(newUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
