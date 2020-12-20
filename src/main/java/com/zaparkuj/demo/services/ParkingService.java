package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Parking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

public interface ParkingService {

    /* ---- Funkcja zwracająca wszystkie parkingi z bazy ---- */
    public ArrayList<Parking> selectAllParkings();
    /* ---- Funkcja zwracająca rekord z bazy o podanym id ---- */
    public Parking selectParking(int id);
}
