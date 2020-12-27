package com.zaparkuj.demo.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation", schema = "parkit")
@Setter
@Getter
@ToString
public class Reservation {

    @Id
    @Column(name = "idreservation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReservation;

    @Column(name = "data_begin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBegin;

    @Column(name = "data_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    @Column(name = "statusreservation")
    private boolean statusReservation;

    @OneToOne
    @JoinColumn(name = "car_idcar")
    private Car car;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_idplace")
    private Place place;

    public Reservation() {

    }

    public Reservation(Date dateBegin, Date dateEnd, boolean statusReservation, Car car, Place place) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.statusReservation = statusReservation;
        this.car = car;
        this.place = place;
    }
}
