package com.zaparkuj.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequest {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String oldPassword;
}