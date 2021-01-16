package com.zaparkuj.demo.utitles.impl;

import com.zaparkuj.demo.utitles.CheckValues;

public class CheckValuesImpl implements CheckValues {

    @Override
    public boolean checkPassword(String password) {
        return false;
    }

    @Override
    public boolean checkEmail(String email) {
        return false;
    }

    @Override
    public boolean checkFirstnameAndLastname(String firstname, String lastname) {
        return false;
    }
}
