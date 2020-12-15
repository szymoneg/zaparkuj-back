package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<String> insertUser(User user, UserRepository userRepository) {
//        System.out.println(user);
//        System.out.println(userRepository.toString());
        //TODO dodac sprawdzenie hasła, zrobic deklaracje userRespository w serviceimpl
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            user.setUsername(user.getUsername());
            user.setPassword_2(user.getPassword_2());
            user.setEmail(user.getEmail());
            userRepository.save(user);
            return new ResponseEntity<>("inserted",HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("user with this username exist",HttpStatus.CONFLICT);
        }
    }
}
