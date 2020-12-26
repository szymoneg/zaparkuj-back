package com.zaparkuj.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CoordsDTO {

    private float latitude;
    private float longitude;

    public CoordsDTO() { }

    public CoordsDTO(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CoordsDTO(String coordinate) {
        int spacePosition = coordinate.indexOf(' ');
        latitude = Float.parseFloat(coordinate.substring(0, spacePosition));
        longitude = Float.parseFloat(coordinate.substring(spacePosition + 1));
    }
}
