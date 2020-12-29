package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.services.ParkingService;
import com.zaparkuj.demo.services.impl.ParkingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class ParkingController {

    @Autowired
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

    /* ---- Funkcja zwracająca listę parkingów w danym mieście ---- */
    @CrossOrigin
    @GetMapping("/parkings/city/{city}")
    public ResponseEntity<ArrayList<Parking>> selectAllCityParkings(@PathVariable("city") String city) {

        ArrayList<Parking> parkings = parkingService.selectAllParkings();
        int lastSpace;

        for(int i = 0; i < parkings.size(); i++) {
            lastSpace = parkings.get(i).getAddress().lastIndexOf(' ');
            System.out.println(parkings.get(i).getAddress().substring(lastSpace + 1));
            if(!parkings.get(i).getAddress().substring(lastSpace + 1).equals(city)) {
                parkings.remove(i);
                i--;
            }
        }

        return new ResponseEntity<>(parkings, HttpStatus.OK);
    }
}
