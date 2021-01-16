package com.zaparkuj.demo.controllers;

import com.zaparkuj.demo.dto.SectorDTO;
import com.zaparkuj.demo.entities.Sector;
import com.zaparkuj.demo.services.SectorService;
import com.zaparkuj.demo.services.impl.SectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class SectorController {

    @Autowired
    SectorService sectorService = new SectorServiceImpl();

    /* ---- Funkcja zwracająca sektor o podanym id ---- */
    @CrossOrigin
    @GetMapping("/sector/{id}")
    public ResponseEntity<SectorDTO> getSectorController(@PathVariable("id") int id) {

        Sector sector = sectorService.selectSector(id);
        SectorDTO sectorDTO = new SectorDTO(sector.getIdSector(), sector.getSectorName(), sector.getPrice());

        return new ResponseEntity<>(sectorDTO, HttpStatus.OK);
    }

    /* ---- Funkcja zwracająca sektory parkingowe na danym parkingu o podanym id ---- */
    @CrossOrigin
    @GetMapping("/sectors/{id}")
    public ResponseEntity<ArrayList<SectorDTO>> getSectorsOnParkingController(@PathVariable("id") int id) {

        ArrayList<SectorDTO> sectorDTOS = new ArrayList<>();
        ArrayList<Sector> sectors = sectorService.selectSectorsOnParking(id);

        for(Sector sector : sectors) {
            sectorDTOS.add(new SectorDTO(sector.getIdSector(), sector.getSectorName(), sector.getPrice()));
        }

        return new ResponseEntity<>(sectorDTOS, HttpStatus.OK);
    }
}
