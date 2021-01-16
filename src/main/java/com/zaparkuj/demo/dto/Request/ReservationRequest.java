package com.zaparkuj.demo.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {

    private int idPlace;
    private int idCar;
    private String dateBegin;
    private String dateEnd;

    public ReservationRequest(int idPlace, int idCar, String dateBegin, String dateEnd) {
        this.idPlace = idPlace;
        this.idCar = idCar;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }
}
