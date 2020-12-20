package com.zaparkuj.demo.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "place", schema = "parkit")
@Setter
@Getter
@ToString
public class Place {

    @Id
    @Column(name = "idplace")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlace;

    @Column(name = "place_name", length = 4)
    private String placeName;

    @Column(name = "status")
    private boolean status;

    @OneToOne
    @JoinColumn(name = "parking_idparking")
    private Parking parking;

    public Place() { }

    public Place(String placeName, boolean status, Parking parking) {
        this.placeName = placeName;
        this.status = status;
        this.parking = parking;
    }
}
