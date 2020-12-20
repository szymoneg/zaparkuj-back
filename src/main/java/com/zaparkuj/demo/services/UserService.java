package com.zaparkuj.demo.services;

import com.zaparkuj.demo.dto.UserDTO;
import com.zaparkuj.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {
    //    boolean insertUser(User user);
//    boolean loginUser(UserDTO userDTO);
    UserDetails loadUserByUsername(String s);

    User save(UserDTO user);
}
