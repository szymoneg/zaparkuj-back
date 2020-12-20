package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.config.JwtTokenUtil;
import com.zaparkuj.demo.dto.*;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.UserService;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("parkings")
    public String parkingList() {
        return "not implemented yet!";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        String email = authenticationRequest.getEmail();
        return ResponseEntity.ok(new JwtResponse(token, email));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(value = "/edit/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> userFormEdit(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getDataUser(email));
    }

    @RequestMapping(value = "/edit/{email}", method = RequestMethod.POST)
    public ResponseEntity<?> userSetEdit(@PathVariable("email") String email, @RequestBody EditUserRequest editUserRequest) {
        userService.editUser(email, editUserRequest);
        return ResponseEntity.ok().build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
