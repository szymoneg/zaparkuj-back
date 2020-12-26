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

    @OneToOne
    @JoinColumn(name = "car_idcar")
    private Car car;

    @OneToOne
    @JoinColumn(name = "place_idplace")
    private Place place;

    public Reservation(Place place, Car car, Date dateBegin, Date dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.car = car;
        this.place = place;
    }

    public Reservation() {

    }
}
