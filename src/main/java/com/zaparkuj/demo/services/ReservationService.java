package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.Place;
import com.zaparkuj.demo.entities.Reservation;

import java.util.ArrayList;
import java.util.Date;

public interface ReservationService {

    /* ---- Funkcja zwracająca dane rezerwacji o podanym id ---- */
    public Reservation getReservation(int id);

    /* ---- Funkcja zwracająca wszystkie rezerwacje ---- */
    public ArrayList<Reservation> getAllReservations();

    /* ---- Funkcja zwracająca wszystkie rezerwacje które powinny być aktywne (w tym momencie) ---- */
    public ArrayList<Reservation> getAllNowActiveReservations();

    /* ---- Funkcja zwracająca wszystkie rezerwacje które powinny być nieaktyne (w tym momencie) ---- */
    public ArrayList<Reservation> getAllNowDesactiveReservations();

    /* ---- Funkcja zwracająca wszystkie aktywne rezerwacje ---- */
    public ArrayList<Reservation> getAllReservations(boolean status);

    /* ---- Funkcja zwracająca wszystkie aktywne/nieaktywne rezerwacje użytkownika o podanym id ---- */
    public ArrayList<Reservation> getUserReservation(int idUser, boolean status);

    /* ---- Funkcja dodająca rezerwacje ---- */
    public boolean insertReservation(Reservation reservation);

    /* ---- Funkcja sprawdzajaca czy w danym czasie jest dostępny parking ---- */
    public boolean checkPlaceToReservation(int idPlace, Date beginDate, Date endDate);

    /* ---- Funkcja usuwająca rezerwacje ---- */
    public boolean deleteReservation(Reservation reservation);

    /* ---- Funkcja zmieniająca status rezerwacji ---- */
    public void changeStatus(int id, boolean status);
}
