package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.repositories.CarRepository;
import com.zaparkuj.demo.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CarServiceImpl implements CarService {

    @Override
    public ResponseEntity<String> insertCar(Car car, CarRepository carRepository) {

        if(carRepository.findByCar(car.getLicencePlate()) != null) {
            carRepository.save(car);
            return new ResponseEntity<>("inserted", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("car with this Licence Plate exist", HttpStatus.CONFLICT);
        }

    }
}
