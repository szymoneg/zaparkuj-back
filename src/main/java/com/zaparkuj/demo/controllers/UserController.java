package com.zaparkuj.demo.controllers;
import com.zaparkuj.demo.config.JwtTokenUtil;
import com.zaparkuj.demo.dto.*;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.UserService;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public String hello(){
        return "Hello world";
    }

//    @GetMapping("parkings")
//    public String parkingList(){
//        return "not implemented yet!";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        if(userService.findUserByEmail(user.getEmail()) == null)
            return ResponseEntity.ok(userService.save(user));
        else
            return new ResponseEntity<>(new MessageDTO("email exist"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/changedata", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserData(@RequestBody UserDataDTO userData) {

        // sprawdzenie poprawności danych
        if(userData.getUsername().equals("") || userData.getFirstname().equals("") || userData.getLastname().equals("") ||
                userData.getUsername().length() > 45 || userData.getFirstname().length() > 45 || userData.getLastname().length() > 45) {
            return new ResponseEntity<>(new MessageDTO("Incorrect data"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserByEmail(userData.getEmail());

        if (user.getUsername() == null || user.getUsername().equals(userData.getUsername())) {

            // sprawdzenie czy taki username istnieje
            User tempUser = userService.findUserByUsername(userData.getUsername());
            if(tempUser != null) {
                if(tempUser.getIdUser() != user.getIdUser())
                    return new ResponseEntity<>(new MessageDTO("Username exist"), HttpStatus.BAD_REQUEST);
            }

            user.setUsername(userData.getUsername());
            user.setFirstname(userData.getFirstname());
            user.setLastname(userData.getLastname());

            return new ResponseEntity<>(userService.saveFullDataUser(user), HttpStatus.OK);
        }


        return new ResponseEntity<>(new MessageDTO("Something is wrong"), HttpStatus.BAD_REQUEST);
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
