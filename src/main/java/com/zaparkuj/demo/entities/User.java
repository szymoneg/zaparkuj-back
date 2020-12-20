package com.zaparkuj.demo.entities;

import lombok.*;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users", schema = "parkit")
public class User {

    @Id
    @Column(name = "idusers")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    String username;
    String email;
    String password;
    String firstname;
    String lastname;
}
