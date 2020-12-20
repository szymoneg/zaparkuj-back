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
    private int idparking;

    @Column(name = "park_name")
    private String parkname;

    @Column(name = "cord")
    private String cord;

    @Column(name = "adress")
    private String adress;

    public Parking() {}

    public Parking(String parkname, String cord, String adress) {
        this.parkname = parkname;
        this.cord = cord;
        this.adress = adress;
    }
}
