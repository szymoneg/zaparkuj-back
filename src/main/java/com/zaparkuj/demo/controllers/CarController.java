package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.CarRepository;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.CarService;
import com.zaparkuj.demo.services.impl.CarServiceImpl;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.module.Configuration;
import java.util.ArrayList;

@RestController
public class CarController {

    //TODO do usuniecia
    @Autowired
    CarRepository carRepository;

//    @Autowired
//    UserRepository userRepository;

    @Autowired
    CarServiceImpl carService;

    @PostMapping("/addCar/{username}")
    public ResponseEntity<String> insertUserController(@PathVariable("username") String userId, @RequestBody Car car){
        return carService.insertCar(car,carRepository,userId);
    }
}
