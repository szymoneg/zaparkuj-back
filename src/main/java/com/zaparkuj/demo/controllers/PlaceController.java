package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.dto.PlaceDTO;
import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.entities.Place;
import com.zaparkuj.demo.services.ParkingService;
import com.zaparkuj.demo.services.PlaceService;
import com.zaparkuj.demo.services.impl.ParkingServiceImpl;
import com.zaparkuj.demo.services.impl.PlaceServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class PlaceController {

    @Autowired
    PlaceService placeService = new PlaceServiceImpl();

//    @Autowired
//    SessionFactory factory = new Configuration()
//            .configure("hibernate.cfg.xml")
//            .addAnnotatedClass(Parking.class)
//            .addAnnotatedClass(Place.class)
//            .buildSessionFactory();

    /* ---- Funkcja zwracająca miejsca parkingowe na danym parkingu o podanym id ---- */
    @CrossOrigin
    @GetMapping("/places/{id}")
    public ResponseEntity<ArrayList<PlaceDTO>> selectPlacesController(@PathVariable("id") int id) {

        List<Place> places = placeService.selectPlaces(id);

//        Session session = factory.openSession();
//        session.beginTransaction();
//
//        Query query = session.createQuery("FROM Place WHERE parking.idparking=" + id);  // pobranie wszystkich miejsc parkingowych o danym id
//        ArrayList<Place> places = (ArrayList<Place>) query.getResultList();
//        System.out.println(places);
//
//        session.getTransaction().commit();
//        session.close();

        // castowanie do PlaceDTO
        ArrayList<PlaceDTO> placeDTOS = places.stream()
                .map(place -> new PlaceDTO(place.getPlaceName(), place.isStatus()))
                .collect(Collectors.toCollection(ArrayList::new));

        return new ResponseEntity<>(placeDTOS, HttpStatus.OK);
    }
}
