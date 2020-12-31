package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.dto.PlaceDTO;
import com.zaparkuj.demo.entities.Place;
import com.zaparkuj.demo.services.PlaceService;
import com.zaparkuj.demo.services.impl.PlaceServiceImpl;
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

    /* ---- Funkcja zwracająca miejsca parkingowe na danym parkingu o podanym id ---- */
    @CrossOrigin
    @GetMapping("/places/{id}")
    public ResponseEntity<ArrayList<PlaceDTO>> selectPlacesController(@PathVariable("id") int id) {

        List<Place> places = placeService.selectPlaces(id);

        // castowanie do PlaceDTO
        ArrayList<PlaceDTO> placeDTOS = places.stream()
                .map(place -> new PlaceDTO(place.getIdPlace(), place.getPlaceName(), place.isStatus()))
                .collect(Collectors.toCollection(ArrayList::new));

        return new ResponseEntity<>(placeDTOS, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca ilość wolnych/zajętych miejsc parkingowych na parkingu o podanym id ---- */
    @CrossOrigin
    @GetMapping("/places/countPlaces/{id}/{status}")
    public ResponseEntity<Long> selectAllFreePlaces(@PathVariable("id") int id, @PathVariable("status") boolean status) {

        Long result = placeService.selectCountPlaces(id, status);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
