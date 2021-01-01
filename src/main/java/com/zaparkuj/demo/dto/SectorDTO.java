package com.zaparkuj.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SectorDTO {

    private int idSector;
    private String sectorName;
    private float price;

    public SectorDTO() { }

    public SectorDTO(int idSector, String sectorName, float price) {
        this.idSector = idSector;
        this.sectorName = sectorName;
        this.price = price;
    }
}
