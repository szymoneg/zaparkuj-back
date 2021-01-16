package com.zaparkuj.demo.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "car", schema = "parkit")
@Setter
@Getter
@ToString
public class Car {

    @Id
    @Column(name = "idcar")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCar;

    @Pattern(regexp = "^[a-zA-Z- ]{0,45}$", message = "bad car mark")
    @Column(name = "mark")
    private String mark;

    @Pattern(regexp = "^[a-zA-Z0-9- ]{0,45}$", message = "bad car model")
    @Column(name = "model")
    private String model;

    @Column(name = "licence_plate", nullable = false)
    @Pattern(regexp = "^([a-zA-z]{3} [a-zA-Z0-9]{4})|([a-zA-z]{2} [a-zA-Z0-9]{5})" +
            "|([a-zA-z]{2}[a-zA-Z0-9]{5})|([a-zA-z]{3}[a-zA-Z0-9]{4})", message = "bad licence plate")
    private String licencePlate;

    @OneToOne
    @JoinColumn(name = "users_idusers")
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
