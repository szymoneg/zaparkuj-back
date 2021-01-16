package com.zaparkuj.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class CarDTO {

    private int idCar;
    private String mark;
    private String model;
    private String licencePlate;

    public CarDTO(int idCar, String mark, String model, String licencePlate) {
        this.idCar = idCar;
        this.mark = mark;
        this.model = model;
        this.licencePlate = licencePlate;
    }
}
