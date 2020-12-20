package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class ParkingController {

    @Autowired
    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Parking.class)
            .buildSessionFactory();

    @CrossOrigin
    @GetMapping("/parking")
    public ResponseEntity<ArrayList<Parking>> selectAllParkingController() {

        System.out.println("Dziala");

        ArrayList<Parking> parkings = new ArrayList<>();

        Session session = factory.openSession();
        session.beginTransaction();

        Parking parking = session.get(Parking.class, 1);

        parkings.add(parking);

        session.getTransaction(). commit();

        System.out.println(parkings);

        session.close();

        return new ResponseEntity<>(parkings, HttpStatus.OK);
    }
}
