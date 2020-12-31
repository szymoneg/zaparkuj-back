package com.zaparkuj.demo.dto.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationResponse {

    private int idReservation;
    private int idPlace;
    private int idCar;
    private Date dateBegin;
    private Date dateEnd;
    private boolean status;

    public ReservationResponse(int idReservation, int idPlace, int idCar, Date dateBegin, Date dateEnd, boolean status) {
        this.idReservation = idReservation;
        this.idPlace = idPlace;
        this.idCar = idCar;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
    }
}
