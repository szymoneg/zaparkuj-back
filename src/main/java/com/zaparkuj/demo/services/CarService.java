package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.repositories.CarRepository;
import org.springframework.http.ResponseEntity;

public interface CarService {

    ResponseEntity<String> insertCar(Car car, CarRepository carRepository);
}
