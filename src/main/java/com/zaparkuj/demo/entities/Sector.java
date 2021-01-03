package com.zaparkuj.demo.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "sector", schema = "parkit")
@Setter
@Getter
@ToString
public class Sector {

    @Id
    @Column(name = "idsector")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSector;

    @Column(name = "sector_name", length = 4)
    private String sectorName;

    @Column(name = "price")
    private float price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_idparking")
    private Parking parking;

    public Sector() { }

    public Sector(String sectorName, float price, com.zaparkuj.demo.entities.Parking parking) {
        this.sectorName = sectorName;
        this.price = price;
        this.parking = parking;
    }
}
