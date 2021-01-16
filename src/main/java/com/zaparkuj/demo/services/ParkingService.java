package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Parking;

import java.util.ArrayList;

public interface ParkingService {

    /* ---- Funkcja zwracająca wszystkie parkingi z bazy ---- */
    public ArrayList<Parking> selectAllParkings();
    /* ---- Funkcja zwracająca parking o danym id ---- */
    public Parking selectParking(int id);
}
