package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Place;

import java.util.ArrayList;
import java.util.List;

public interface PlaceService {

    /* ---- Funkcja zwracająca wszystkie miejsca z parkingu o podanym id ---- */
    public ArrayList<Place> selectPlaces(int id);
}
