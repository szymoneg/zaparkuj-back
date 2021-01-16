package com.zaparkuj.demo.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "parking", schema = "parkit")
@Setter
@Getter
@ToString
public class Parking {

    @Id
    @Column(name = "idparking")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParking;

    @Column(name = "park_name")
    private String parkname;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "address")
    private String address;

    public Parking() {}

    public Parking(String parkname, float latitude, float longitude, String address) {
        this.parkname = parkname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
