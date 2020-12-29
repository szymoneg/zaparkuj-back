package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.repositories.CarRepository;
import com.zaparkuj.demo.repositories.UserRepository;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface CarService {

    /* ---- Funkcja wybiera wszystkie samochody użytkownika o danym username ---- */
    ArrayList<Car> selectCarsUser(String user);

}
