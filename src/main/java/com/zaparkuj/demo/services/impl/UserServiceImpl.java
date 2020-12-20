package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.dto.EditUserRequest;
import com.zaparkuj.demo.dto.UserDTO;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if (user == null){
            throw new UsernameNotFoundException("User not found wtih username: " + s);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public User save(UserDTO user) {
         User newUser = new User();
         newUser.setEmail(user.getEmail());
         newUser.setPassword(passwordEncoder.encode(user.getPassword()));
         return userRepository.save(newUser);
    }

    @Override
    public Optional<User> editUser(Long id, EditUserRequest editUserRequest) {
        return Optional.empty();
    }
}
