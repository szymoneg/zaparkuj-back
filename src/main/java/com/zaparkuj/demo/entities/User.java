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

    String username;
    String email;
    String password_2;
    String firstname;
    String lastname;
}
