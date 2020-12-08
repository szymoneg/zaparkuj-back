package com.zaparkuj.demo.repositories;

import com.zaparkuj.demo.entities.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    Car findByCar(String licenceNumber);
}
