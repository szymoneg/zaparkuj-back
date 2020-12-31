package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.dto.MessageDTO;
import com.zaparkuj.demo.dto.Request.ReservationRequest;
import com.zaparkuj.demo.dto.Response.ReservationResponse;
import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.Place;
import com.zaparkuj.demo.entities.Reservation;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.CarService;
import com.zaparkuj.demo.services.PlaceService;
import com.zaparkuj.demo.services.ReservationService;
import com.zaparkuj.demo.services.UserService;
import com.zaparkuj.demo.services.impl.CarServiceImpl;
import com.zaparkuj.demo.services.impl.PlaceServiceImpl;
import com.zaparkuj.demo.services.impl.ReservationServiceImpl;
import com.zaparkuj.demo.services.impl.UserServiceImpl;
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

    /* ---- Funkcja zwracająca wszystkie rezerwacje ---- */
    @CrossOrigin
    @GetMapping("/reservations")
    public ResponseEntity<ArrayList<ReservationResponse>> selectAllReservations() {

        ArrayList<Reservation> reservations = reservationService.getAllReservations();
        ArrayList<ReservationResponse> reservationResponses = new ArrayList<>();

        for(Reservation reservation : reservations) {
            reservationResponses.add(new ReservationResponse(reservation.getIdReservation(),
                    reservation.getPlace().getIdPlace(), reservation.getCar().getIdCar(),
                    reservation.getDateBegin(), reservation.getDateBegin(), reservation.isStatusReservation()));
        }

        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca dane rezerwacji o podanym id ---- */
    @CrossOrigin
    @GetMapping("/reservation/id/{id}")
    public ResponseEntity<ReservationResponse> selectReservation(@PathVariable("id") int id) {

        Reservation reservation = reservationService.getReservation(id);
        ReservationResponse reservationResponse = new ReservationResponse( reservation.getIdReservation(),
                reservation.getPlace().getIdPlace(), reservation.getCar().getIdCar(),
                reservation.getDateBegin(), reservation.getDateEnd(), reservation.isStatusReservation());

        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca wszystkie dane rezerwacji użytkownika o podanej nazwie username ---- */
    @CrossOrigin
    @GetMapping("/reservation/user/{username}")
    public ResponseEntity<ArrayList<ReservationResponse>> selectReservationOfUsername(@PathVariable("username") String username) {

        ArrayList<Reservation> reservations = reservationService.getAllReservations();
        ArrayList<ReservationResponse> reservationResponses = new ArrayList<>();

        for(int i = 0; i < reservations.size(); i++) {
            if(!reservations.get(i).getCar().getHolderCar().getUsername().equals(username)) {
                reservations.remove(i);
                i--;
            }
            else {
                reservationResponses.add(new ReservationResponse(reservations.get(i).getIdReservation(),
                        reservations.get(i).getPlace().getIdPlace(), reservations.get(i).getCar().getIdCar(),
                        reservations.get(i).getDateBegin(), reservations.get(i).getDateBegin(),
                        reservations.get(i).isStatusReservation()));
            }
        }

        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca wszystkie aktywne dane rezerwacji użytkownika o podanej nazwie username ---- */
    @CrossOrigin
    @GetMapping("/reservation/status/{status}/{username}")
    public ResponseEntity<ArrayList<ReservationResponse>> selectReservationTimeOfUsername(
            @PathVariable("status") boolean status,
            @PathVariable("username") String username) {

        ArrayList<ReservationResponse> reservationResponses = new ArrayList<>();
        User user = userService.findUserByUsername(username);
        ArrayList<Reservation> reservations = reservationService.getUserReservation(user.getIdUser(), status);

        for(Reservation reservation : reservations) {
            reservationResponses.add(new ReservationResponse(reservation.getIdReservation(),
                    reservation.getPlace().getIdPlace(), reservation.getCar().getIdCar(),
                    reservation.getDateBegin(), reservation.getDateEnd(), reservation.isStatusReservation()));
        }
        
        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    /* ---- Funkcja dodająca rezerwacje na podstawie przesłanego JSONA ---- */
    @CrossOrigin
    @RequestMapping(value = "/reservation/add", method = RequestMethod.POST)
    public ResponseEntity<?> addUserReservation(@RequestBody ReservationRequest reservationRequest) {

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

        ArrayList<Reservation> reservations = reservationService.getAllActiveReservations();

        for(Reservation reservation : reservations)
            reservationService.desactiveReservation(reservation);
    }
}
