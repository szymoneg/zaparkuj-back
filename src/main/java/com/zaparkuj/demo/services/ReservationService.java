package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Reservation;

import java.util.ArrayList;

public interface ReservationService {

    /* ---- Funkcja zwracająca dane rezerwacji o podanym id ---- */
    public Reservation getReservation(int id);

    /* ---- Funkcja zwracająca wszystkie rezerwacje ---- */
    public ArrayList<Reservation> getAllReservations();
}
