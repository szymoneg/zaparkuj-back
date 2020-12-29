package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.dto.Response.CarResponse;
import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.CarService;
import com.zaparkuj.demo.services.UserService;
import com.zaparkuj.demo.services.impl.CarServiceImpl;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class CarController {

    @Autowired
    CarService carService = new CarServiceImpl();

    @Autowired
    UserService userService = new UserServiceImpl();

    @CrossOrigin
    @GetMapping("/cars/{username}")
    public ResponseEntity<ArrayList<Car>> selectCarsOfUser(@PathVariable("username") String username) {

        ArrayList<Car> cars = carService.selectCarsUser(username);

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @RequestMapping(value = "/addcar/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> addCar(@RequestBody CarResponse car, @PathVariable("username") String username) {

        User user = userService.findUserByUsername(username);

        carService.insertCar(user, car.getMark(), car.getModel(), car.getLicencePlate());

        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }



}
