package com.zaparkuj.demo.entities;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long idUser;

    String username;
    String password;
    String name;
    String lastname;
    String email;
}
