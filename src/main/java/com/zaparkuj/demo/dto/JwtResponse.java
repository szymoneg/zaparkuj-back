package com.zaparkuj.demo.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;

    public JwtResponse(String jwttoken, String email) {
        this.jwttoken = jwttoken;
        this.username = email;
    }

    public String getUsername() {
        return username;
    }

    public String getJwttoken() {
        return jwttoken;
    }
}
