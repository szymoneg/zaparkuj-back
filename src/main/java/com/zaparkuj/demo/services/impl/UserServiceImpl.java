package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.dto.UserRequest;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean insertUser(User user) {
        //TODO dodac sprawdzenie hasła
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            user.setUsername(user.getUsername());
            user.setPassword(user.getPassword());
            user.setEmail(user.getEmail());
            userRepository.save(user);
            return true;
        }else{
            return false; // TODO zamienic na enum'a
        }
    }

    @Override
    public boolean loginUser(UserRequest userRequest) {
        if (userRepository.findUserByPasswordAndEmail(userRequest.getPassword(), userRequest.getEmail())==null){
            return false;
        }else {
            return true;
        }
    }
}
