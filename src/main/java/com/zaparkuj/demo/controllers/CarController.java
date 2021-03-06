package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.dto.MessageDTO;
import com.zaparkuj.demo.dto.CarDTO;
import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.CarService;
import com.zaparkuj.demo.services.UserService;
import com.zaparkuj.demo.services.impl.CarServiceImpl;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class CarController {

    @Autowired
    CarService carService = new CarServiceImpl();

    @Autowired
    UserService userService = new UserServiceImpl();

    /* ---- Funkcja wybierająca wszystkie samochody należące do użytkownika o podanym username ---- */
    @CrossOrigin
    @GetMapping("/cars/{username}")
    public ResponseEntity<ArrayList<CarDTO>> getCarsOfUserController(@PathVariable("username") String username) {

        ArrayList<Car> cars = carService.selectCarsUser(username);
        ArrayList<CarDTO> carDTOS = new ArrayList<>();

        for(Car car : cars) {
            carDTOS.add(new CarDTO(car.getIdCar(), car.getMark(), car.getModel(), car.getLicencePlate()));
        }

        return new ResponseEntity<>(carDTOS, HttpStatus.OK);
    }

    /* ---- Funkcja dodająca samochód do użytkownika o podanym username ---- */
    @RequestMapping(value = "/addcar/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> addCarController(@RequestBody CarDTO car, @PathVariable("username") String username) {

        User user = userService.findUserByUsername(username);

        if(carService.selectCarOfLicencePlate(car.getLicencePlate()) != null)
            return new ResponseEntity<>(new MessageDTO("licence plate exist"), HttpStatus.BAD_REQUEST);

        try {
            carService.insertCar(user, car.getMark(), car.getModel(), car.getLicencePlate().toUpperCase());
        }
        catch (ValidationException exception) {
            return new ResponseEntity<>(new MessageDTO(exception), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new MessageDTO("created"), HttpStatus.CREATED);
    }

    /* ---- Funkcja zmieniająca dane samochodu użytkownika ---- */
    @RequestMapping(value = "/changecar", method = RequestMethod.POST)
    public ResponseEntity<?> changeCarController(@RequestBody CarDTO car) {

        Car userCar = carService.selectCarOfLicencePlate(car.getLicencePlate());
        if(userCar != null)
            if(userCar.getIdCar() != car.getIdCar())
                return new ResponseEntity<>(new MessageDTO("licence plate exist"), HttpStatus.BAD_REQUEST);

        try {
            userCar = carService.selectCarOfId(car.getIdCar());
            userCar.setLicencePlate(car.getLicencePlate());
            userCar.setMark(car.getMark());
            userCar.setModel(car.getModel());
            carService.updateCar(userCar);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new MessageDTO("wrong car data"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new MessageDTO("changed"), HttpStatus.OK);
    }

    /* ---- Funkcja usuwająca samochód o podanym id ---- */
    @RequestMapping(value = "/deletecar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteCarOfIdController(@PathVariable("id") int id) {

        Car car = carService.selectCarOfId(id);
        if(car == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        boolean result = carService.deleteCar(car);

        if(result)
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

    }
}
