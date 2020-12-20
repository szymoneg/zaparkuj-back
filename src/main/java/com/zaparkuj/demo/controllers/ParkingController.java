package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.services.ParkingService;
import com.zaparkuj.demo.services.impl.ParkingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class ParkingController {

    ParkingService parkingService = new ParkingServiceImpl();

    /* ---- Funkcja zwracająca parking o podanym id ---- */
    @CrossOrigin
    @GetMapping("/parking/{id}")
    public ResponseEntity<Parking> selectParkingController(@PathVariable("id") int id) {

        Parking parking = parkingService.selectParking(id);

        return new ResponseEntity<>(parking, HttpStatus.OK);
    }

    /* ---- Funkcja zwracjąca wszystkie dostępne parkingi z bazy danych ---- */
    @CrossOrigin
    @GetMapping("/parkings")
    public ResponseEntity<ArrayList<Parking>> selectAllParkingsController() {

        ArrayList<Parking> parkings = parkingService.selectAllParkings();

        return new ResponseEntity<>(parkings, HttpStatus.OK);
    }
}
