package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.CarRepository;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.CarService;
import com.zaparkuj.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarServiceImpl {

//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public ResponseEntity<String> insertCar(Car car, CarRepository carRepository, String name) {
//        ArrayList<User> user = userRepository.findByUsername(name);
//        car.setHolderCar(user.get(0));
//        if(carRepository.findByLicencePlate(car.getLicencePlate()).isEmpty()) {
//            carRepository.save(car);
//            return new ResponseEntity<>("inserted", HttpStatus.ACCEPTED);
//        } else {
//            return new ResponseEntity<>("car with this Licence Plate exist", HttpStatus.CONFLICT);
//        }
//    }
}
