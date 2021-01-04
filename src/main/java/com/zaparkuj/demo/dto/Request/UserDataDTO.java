package com.zaparkuj.demo.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDataDTO {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
}
