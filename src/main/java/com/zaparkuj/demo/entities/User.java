package com.zaparkuj.demo.entities;

import lombok.*;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "idusers")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUser;

    @Column(name = "username")
    private String username;

    @Column(name = "password_2")
    private String password;

    @Column(name = "firstname")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    public User(String username, String password, String name, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public User() {

    }
}
