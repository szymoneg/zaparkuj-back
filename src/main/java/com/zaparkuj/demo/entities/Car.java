package com.zaparkuj.demo.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "car")
@Setter
@Getter
@ToString
public class Car {

    @Id
    @Column(name = "idcar")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCar;

    @Column(name = "mark")
    private String mark;

    @Column(name = "model")
    private String model;

    @Column(name = "licence_plate")
    private String licencePlate;

    @Column(name = "users_idusers")
    @OneToOne
    private User holderCar;

    public Car(String mark, String model, String licencePlate, User holderCar) {
        this.mark = mark;
        this.model = model;
        this.licencePlate = licencePlate;
        this.holderCar = holderCar;
    }

    public Car() {

    }
}
