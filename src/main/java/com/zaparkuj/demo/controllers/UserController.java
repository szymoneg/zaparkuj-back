package com.zaparkuj.demo.controllers;
import com.zaparkuj.demo.config.JwtTokenUtil;
import com.zaparkuj.demo.dto.JwtRequest;
import com.zaparkuj.demo.dto.JwtResponse;
import com.zaparkuj.demo.dto.UserDTO;
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

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @CrossOrigin
//    @PostMapping("/register")
//    public ResponseEntity<String> insertUserController(@RequestBody User user){
//        if (userService.insertUser(user)){
//            return new ResponseEntity<>("Dodano!",HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>("Nie dodano", HttpStatus.FORBIDDEN);
//        }
//    }
//
//    @CrossOrigin
//    @PostMapping("/login")
//    public Boolean loginUserController(@RequestBody UserDTO userDTO){
//        return userService.loginUser(userDTO);
//    }


    @GetMapping("/hello")
    @CrossOrigin
    public String hello(){
        return "Hello world";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userService.save(user));
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
