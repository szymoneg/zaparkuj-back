package com.zaparkuj.demo.services;

import com.zaparkuj.demo.entities.Sector;

import java.util.ArrayList;

public interface SectorService {

    /* ---- Funkcja zwracająca dane sektora o podanym id ---- */
    public Sector selectSector(int idSector);

    /* ---- Funkcja zwracająca wszystkie sektory z parkingu o podanym id ---- */
    public ArrayList<Sector> selectSectorsOnParking(int idParking);
}
