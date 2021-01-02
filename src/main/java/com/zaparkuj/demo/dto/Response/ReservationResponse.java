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
    private String licencePlate;
    private Date dateBegin;
    private Date dateEnd;
    private boolean status;

    public ReservationResponse(int idReservation, String placeName, String licencePlate, Date dateBegin, Date dateEnd, boolean status) {
        this.idReservation = idReservation;
        this.placeName = placeName;
        this.licencePlate = licencePlate;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
    }
}
