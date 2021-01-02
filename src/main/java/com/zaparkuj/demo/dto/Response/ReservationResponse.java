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
    private String placeName;
    private String carMark;
    private String licencePlate;
    private String parkingAddress;
    private Date dateBegin;
    private Date dateEnd;
    private boolean status;

    public ReservationResponse(int idReservation, String placeName, String carMark, String licencePlate, String parkingAddress, Date dateBegin, Date dateEnd, boolean status) {
        this.idReservation = idReservation;
        this.placeName = placeName;
        this.carMark = carMark;
        this.licencePlate = licencePlate;
        this.parkingAddress = parkingAddress;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
    }
}
