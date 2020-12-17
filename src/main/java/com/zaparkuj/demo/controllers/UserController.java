package com.zaparkuj.demo.controllers;
import com.zaparkuj.demo.dto.UserRequest;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<String> insertUserController(@RequestBody User user){
        if (userService.insertUser(user)){
            return new ResponseEntity<>("Dodano!",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Nie dodano", HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public Boolean loginUserController(@RequestBody UserRequest userRequest){
        return userService.loginUser(userRequest);
    }
}
