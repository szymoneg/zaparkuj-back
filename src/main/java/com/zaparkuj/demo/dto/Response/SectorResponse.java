package com.zaparkuj.demo.dto.Response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SectorResponse {

    private int idSector;
    private String sectorName;
    private int freePlaces;
    private int occupatePlaces;
    private float price;

    public SectorResponse() { }

    public SectorResponse(int idSector, String sectorName, int freePlaces, int occupatePlaces, float price) {
        this.idSector = idSector;
        this.sectorName = sectorName;
        this.freePlaces = freePlaces;
        this.occupatePlaces = occupatePlaces;
        this.price = price;
    }
}
