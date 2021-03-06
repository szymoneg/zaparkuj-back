package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Place;

import java.util.ArrayList;
import java.util.List;

public interface PlaceService {

    /* ---- Funkcja zwracająca wszystkie miejsca z sektora o podanym id ---- */
    public ArrayList<Place> selectPlaces(int id);

    /* ---- Funkcja zwracająca ilość wolnych/zajętych miejsc ---- */
    // id - id sektora
    // status - status true to miejsca wolne, status false to miejsca zajęte
    public long selectCountPlaces(int id, boolean status);

    /* ---- Funkcja zwracająca miejsce parkingowe o danym id ---- */
    public Place selectPlace(int id);

    /* ---- Funkcja zmienia status miejsca parkingowego ---- */
    public void changePlace(int id, boolean status);
}
