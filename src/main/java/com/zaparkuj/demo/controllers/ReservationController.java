package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.entities.Reservation;
import com.zaparkuj.demo.services.ReservationService;
import com.zaparkuj.demo.services.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService = new ReservationServiceImpl();

    /* ---- Funkcja zwracająca wszystkie rezerwacje ---- */
    @CrossOrigin
    @GetMapping("/reservations")
    public ResponseEntity<ArrayList<Reservation>> selectAllReservations() {

        ArrayList<Reservation> reservations = reservationService.getAllReservations();

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca dane rezerwacji o podanym id ---- */
    @CrossOrigin
    @GetMapping("/reservation/{id}")
    public ResponseEntity<Reservation> selectReservation(@PathVariable("id") int id) {

        Reservation reservation = reservationService.getReservation(id);

        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca wszystkie dane rezerwacji użytkownika o podanej nazwie username ---- */
    @CrossOrigin
    @GetMapping("/reservation/user/{username}")
    public ResponseEntity<ArrayList<Reservation>> selectReservationOfUsername(@PathVariable("username") String username) {

        ArrayList<Reservation> reservations = reservationService.getAllReservations();

        for(int i = 0; i < reservations.size(); i++) {
            if(!reservations.get(i).getCar().getHolderCar().getUsername().equals(username)) {
                reservations.remove(i);
                i--;
            }
        }

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca wszystkie aktywne dane rezerwacji użytkownika o podanej nazwie username ---- */
    @CrossOrigin
    @GetMapping("/reservation/active/{username}")
    public ResponseEntity<ArrayList<Reservation>> selectReservationTimeOfUsername(@PathVariable("username") String username) {

        ArrayList<Reservation> reservations = reservationService.getAllReservations();
        long systemTime = System.currentTimeMillis();                                           // czas systemowy w milisekundach

        for(int i = 0; i < reservations.size(); i++) {
            if(!reservations.get(i).getCar().getHolderCar().getUsername().equals(username)) {
                reservations.remove(i);
                i--;
                continue;
            }

            long dateBeginAsMilis = reservations.get(i).getDateBegin().getTime();               // start rezerwacji
            long dateEndAsMilis = reservations.get(i).getDateEnd().getTime();                   // koniec rezerwacji

            if(!(systemTime > dateBeginAsMilis && systemTime < dateEndAsMilis)) {
                reservations.remove(i);
                i--;
            }
        }

        return new ResponseEntity<>(reservations, HttpStatus.OK);
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
