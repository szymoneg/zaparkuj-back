package com.zaparkuj.demo.entities;

import lombok.*;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "users", schema = "parkit")
public class User {

    @Id
    @Column(name = "idusers")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,45}$")
    String username;

    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    String email;
    String password;

    @Pattern(regexp = "^[a-zA-Z ]{0,45}$")
    String firstname;
    @Pattern(regexp = "^[a-zA-Z- ]{0,45}$")
    String lastname;

    public User() { }

    public User(String username, String email, String password, String firstname, String lastname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
