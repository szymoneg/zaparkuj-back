package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.dto.MessageDTO;
import com.zaparkuj.demo.dto.PlaceDTO;
import com.zaparkuj.demo.dto.Request.ReservationRequest;
import com.zaparkuj.demo.dto.Response.DateResponse;
import com.zaparkuj.demo.dto.Response.ReservationResponse;
import com.zaparkuj.demo.dto.Response.SectorResponse;
import com.zaparkuj.demo.entities.*;
import com.zaparkuj.demo.services.*;
import com.zaparkuj.demo.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService = new ReservationServiceImpl();

    @Autowired
    private UserService userService = new UserServiceImpl();

    @Autowired
    private CarService carService = new CarServiceImpl();

    @Autowired
    private PlaceService placeService = new PlaceServiceImpl();

    @Autowired
    private SectorService sectorService = new SectorServiceImpl();

    /* ---- Funkcja zwracająca wszystkie rezerwacje ---- */
    @CrossOrigin
    @GetMapping("/reservations")
    public ResponseEntity<ArrayList<ReservationResponse>> getAllReservationsController() {

        ArrayList<Reservation> reservations = reservationService.getAllReservations();
        ArrayList<ReservationResponse> reservationResponses = new ArrayList<>();

            for(Reservation reservation : reservations) {
            reservationResponses.add(new ReservationResponse(reservation.getIdReservation(),
                    reservation.getPlace().getPlaceName(), reservation.getCar().getMark(),
                    reservation.getCar().getLicencePlate(), reservation.getPlace().getSector().getParking().getAddress(),
                    reservation.getDateBegin(), reservation.getDateBegin(), reservation.isStatusReservation()));
        }

        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca dane rezerwacji o podanym id ---- */
    @CrossOrigin
    @GetMapping("/reservation/id/{id}")
    public ResponseEntity<ReservationResponse> getReservationController(@PathVariable("id") int id) {

            Reservation reservation = reservationService.getReservation(id);
        ReservationResponse reservationResponse = new ReservationResponse( reservation.getIdReservation(),
                reservation.getPlace().getPlaceName(), reservation.getCar().getMark(),
                reservation.getCar().getLicencePlate(), reservation.getPlace().getSector().getParking().getAddress(),
                reservation.getDateBegin(), reservation.getDateEnd(), reservation.isStatusReservation());

        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca wszystkie dane rezerwacji użytkownika o podanej nazwie username ---- */
    @CrossOrigin
    @GetMapping("/reservation/user/{username}")
    public ResponseEntity<ArrayList<ReservationResponse>> getReservationOfUsernameController(@PathVariable("username") String username) {

        ArrayList<Reservation> reservations = reservationService.getAllReservations();
        ArrayList<ReservationResponse> reservationResponses = new ArrayList<>();

        for(int i = 0; i < reservations.size(); i++) {
            if(!reservations.get(i).getCar().getHolderCar().getUsername().equals(username)) {
                reservations.remove(i);
                i--;
            }
            else {
                    reservationResponses.add(new ReservationResponse(reservations.get(i).getIdReservation(),
                        reservations.get(i).getPlace().getPlaceName(), reservations.get(i).getCar().getMark(),
                        reservations.get(i).getCar().getLicencePlate(), reservations.get(i).getPlace().getSector().getParking().getAddress(),
                        reservations.get(i).getDateBegin(), reservations.get(i).getDateBegin(),
                        reservations.get(i).isStatusReservation()));
            }
        }

        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca wszystkie aktywne dane rezerwacji użytkownika o podanej nazwie username ---- */
    @CrossOrigin
    @GetMapping("/reservation/status/{status}/{username}")
    public ResponseEntity<ArrayList<ReservationResponse>> getReservationTimeOfUsernameController(
            @PathVariable("status") boolean status,
            @PathVariable("username") String username) {

        ArrayList<ReservationResponse> reservationResponses = new ArrayList<>();
        User user = userService.findUserByUsername(username);
        ArrayList<Reservation> reservations = reservationService.getUserReservation(user.getIdUser(), status);

            for(Reservation reservation : reservations) {
            reservationResponses.add(new ReservationResponse(reservation.getIdReservation(),
                    reservation.getPlace().getPlaceName(), reservation.getCar().getMark(),
                    reservation.getCar().getLicencePlate(), reservation.getPlace().getSector().getParking().getAddress(),
                    reservation.getDateBegin(), reservation.getDateEnd(), reservation.isStatusReservation()));
        }
        
        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    /* Funkcja zwracająca ilość wolnych/zajętych miejsc parkingowych w sektorach
    na parkingu o podanym id między przesłanymi godzinami */
    @CrossOrigin
    @PostMapping("/sector/countsector/{id}")
    public ResponseEntity<?> getCountSectorInParking(
            @RequestBody DateResponse dateResponse, @PathVariable("id") int id) throws ParseException {

        Date dateBegin;
        Date dateEnd;
        try {
            dateBegin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(dateResponse.getDateBegin());
            dateEnd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(dateResponse.getDateEnd());

            if(dateBegin.getTime() > dateEnd.getTime())
                return new ResponseEntity<>(new MessageDTO("bad date begin and end reservation"), HttpStatus.BAD_REQUEST);
        }
        catch (ParseException exception) {
            return new ResponseEntity<>(new MessageDTO("bad data format"), HttpStatus.BAD_REQUEST);
        }

        ArrayList<SectorResponse> sectorResponses = new ArrayList<>();
        ArrayList<Sector> sectors = sectorService.selectSectorsOnParking(id);
        ArrayList<Reservation> reservations = reservationService.getAllReservations(true);

        for(Sector sector : sectors) {
            long t = placeService.selectCountPlaces(sector.getIdSector(), true);
            long f = placeService.selectCountPlaces(sector.getIdSector(), false);
            sectorResponses.add(new SectorResponse(sector.getIdSector(), sector.getSectorName(), (int) (t+f), 0, sector.getPrice()));
        }

        for(Reservation reservation : reservations) {
            for(int i = 0; i < sectorResponses.size(); i++) {
                if(reservation.getPlace().getSector().getIdSector() == sectorResponses.get(i).getIdSector()) {
                    ArrayList<Place> places = placeService.selectPlaces(sectorResponses.get(i).getIdSector());
                    for(Place place : places) {
                        if(place.getIdPlace() == reservation.getPlace().getIdPlace()) {
                            sectorResponses.get(i).setFreePlaces(sectorResponses.get(i).getFreePlaces()-1);
                            sectorResponses.get(i).setOccupatePlaces(sectorResponses.get(i).getOccupatePlaces()+1);
                        }
                    }
                }
            }
        }

        return new ResponseEntity<>(sectorResponses, HttpStatus.OK);
    }

    /* ---- Funkcja zwraca listę miejsc parkingowych dostępnych/niedostępnych
    w sektorze o przesłanym id oraz przesłanych godzinach ---- */
    @CrossOrigin
    @PostMapping("/place/countplaces/{id}")
    public ResponseEntity<?> getCountPlacesInSector(
            @PathVariable("id") int id, @RequestBody DateResponse dateResponse) {

        Date dateBegin;
        Date dateEnd;
        try {
            dateBegin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(dateResponse.getDateBegin());
            dateEnd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(dateResponse.getDateEnd());

            if(dateBegin.getTime() > dateEnd.getTime())
                return new ResponseEntity<>(new MessageDTO("bad date begin and end reservation"), HttpStatus.BAD_REQUEST);
        }
        catch (ParseException exception) {
            return new ResponseEntity<>(new MessageDTO("bad data format"), HttpStatus.BAD_REQUEST);
        }

        ArrayList<PlaceDTO> placeDTOS = new ArrayList<>();
        ArrayList<Place> places = placeService.selectPlaces(id);
        ArrayList<Reservation> reservations = reservationService.getAllReservations(true);

        for(Place place : places) {
            placeDTOS.add(new PlaceDTO(place.getIdPlace(), place.getPlaceName(), true));
        }

        for(Reservation reservation : reservations) {
            for(int i = 0; i < placeDTOS.size(); i++) {
                if(reservation.getPlace().getIdPlace() == placeDTOS.get(i).getIdPlace()) {
                    if(!reservationService.checkPlaceToReservation(placeDTOS.get(i).getIdPlace(), dateBegin, dateEnd))
                        placeDTOS.get(i).setStatus(false);
                }
            }
        }

        return new ResponseEntity<>(placeDTOS, HttpStatus.OK);
    }

    /* ---- Funkcja dodająca rezerwacje na podstawie przesłanego JSONA ---- */
    @CrossOrigin
    @RequestMapping(value = "/reservation/add", method = RequestMethod.POST)
    public ResponseEntity<?> addUserReservationController(@RequestBody ReservationRequest reservationRequest) {

        try {
            Car car = carService.selectCarOfId(reservationRequest.getIdCar());
            Place place = placeService.selectPlace(reservationRequest.getIdPlace());
            if(car == null || place == null) {
                return new ResponseEntity<>(new MessageDTO("not found data"), HttpStatus.BAD_REQUEST);
            }

            Date dateBegin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(reservationRequest.getDateBegin());
            Date dateEnd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(reservationRequest.getDateEnd());

            if(dateBegin.getTime() > dateEnd.getTime())
                return new ResponseEntity<>(new MessageDTO("bad date begin and end reservation"), HttpStatus.BAD_REQUEST);
            if(dateBegin.getTime() == dateEnd.getTime())
                return new ResponseEntity<>(new MessageDTO("date begin and end reservation are the same"), HttpStatus.BAD_REQUEST);

            boolean checkTime = reservationService.checkPlaceToReservation(reservationRequest.getIdPlace(), dateBegin, dateEnd);
            if(!checkTime)
                return new ResponseEntity<>(new MessageDTO("bad time reservation"), HttpStatus.BAD_REQUEST);

            Reservation reservation = new Reservation(dateBegin, dateEnd, true, car, place);

            reservationService.insertReservation(reservation);
        }
        catch (ValidationException exception) {
            return new ResponseEntity<>(new MessageDTO(exception), HttpStatus.BAD_REQUEST);
        }
        catch (ParseException exception) {
            return new ResponseEntity<>(new MessageDTO("bad data format"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new MessageDTO("created"), HttpStatus.CREATED);
    }

    /* ---- Funkcja wykonuje się na starcie i co określony czas sprawdza aktywne rezerwacje,
            które powinny się skończyć i zmienia ich status ---- */
    @Scheduled(fixedRate = 300000)
    public void checkReservations() {

        ArrayList<Reservation> reservations = reservationService.getAllNowActiveReservations();

        for(Reservation reservation : reservations)
            reservationService.desactiveReservation(reservation);
    }
}
