package com.zaparkuj.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PlaceDTO {

    private int idPlace;
    private String placeName;
    private boolean status;

    public PlaceDTO() { }

    public PlaceDTO(int idPlace, String placeName, boolean status) {
        this.idPlace = idPlace;
        this.placeName = placeName;
        this.status = status;
    }
}