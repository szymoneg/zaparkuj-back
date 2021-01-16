package com.zaparkuj.demo.controllers;
import com.zaparkuj.demo.config.JwtTokenUtil;
import com.zaparkuj.demo.dto.*;
import com.zaparkuj.demo.dto.Request.UserDataDTO;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.MailService;
import com.zaparkuj.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.ValidationException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private MailService mailService;

    @Autowired
    public UserController(MailService mailService) {
        this.mailService = mailService;
    }

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

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        String username = authenticationRequest.getUsername();

        return ResponseEntity.ok(new JwtResponse(token,username));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        if(userService.findUserByEmail(user.getEmail()) == null && userService.findUserByUsername(user.getUsername()) == null) {
            try {
                return ResponseEntity.ok(userService.save(user));
            }
            catch (ValidationException exception) {
                return new ResponseEntity<>(new MessageDTO("bad format username or email"), HttpStatus.BAD_REQUEST);
            }
        }
        else
            return new ResponseEntity<>(new MessageDTO("email or login exist"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/changedata", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserData(@RequestBody UserDataDTO userData) {

        User user = userService.findUserByUsername(userData.getUsername());

        if(user == null)
            return new ResponseEntity<>(new MessageDTO("not found user"), HttpStatus.NOT_FOUND);
        else {
            User tempUser = userService.findUserByEmail(userData.getEmail());
            if(tempUser != null)
                if(!user.getIdUser().equals(tempUser.getIdUser()))
                    return new ResponseEntity<>(new MessageDTO("email exist"), HttpStatus.BAD_REQUEST);
        }

        try{
            user.setEmail(userData.getEmail());
            user.setFirstname(userData.getFirstname());
            user.setLastname(userData.getLastname());
            userService.saveFullDataUser(user);
        }
        catch (ValidationException exception) {
            return new ResponseEntity<>(new MessageDTO("validation exception"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new MessageDTO("changed"), HttpStatus.OK);
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

    @GetMapping("/sendmail/{username}")
    public ResponseEntity<?> sendMailWithNewPasswordController(@PathVariable("username") String userName) throws MessagingException {

        final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String generatePassword = "";
        int len = 7;
        for(int i = 0; i < len; i++) {
            generatePassword += chars.charAt(new Random().nextInt(chars.length()));
        }

        userService.updatePassword(userName, generatePassword);

        User user = userService.findUserByUsername(userName);

        mailService.sendMail(user.getEmail(), "Przypomnienie hasła",
                "nowe hasło: " + generatePassword, true);

        return new ResponseEntity<>(new MessageDTO("sends"), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/changepassword", method = RequestMethod.POST)
    public ResponseEntity<?> changeUserPasswordController(@RequestBody UserDTO userDTO) {

        User user = userService.findUserByUsername(userDTO.getUsername());
        if(user == null)
            user = userService.findUserByEmail(userDTO.getEmail());
        if(user == null)
            return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.BAD_REQUEST);

        userService.updatePassword(user.getUsername(), userDTO.getPassword());

        return new ResponseEntity<>(new MessageDTO("changed"), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> userFormEdit(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getDataUser(username));
    }
}
