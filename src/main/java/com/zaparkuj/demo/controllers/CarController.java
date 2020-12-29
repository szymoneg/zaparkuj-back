package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.services.CarService;
import com.zaparkuj.demo.services.impl.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class CarController {

    @Autowired
    CarService carService = new CarServiceImpl();

    @CrossOrigin
    @GetMapping("/cars/{username}")
    public ResponseEntity<ArrayList<Car>> selectCarsOfUser(@PathVariable("username") String username) {

        ArrayList<Car> cars = carService.selectCarsUser(username);

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

}
