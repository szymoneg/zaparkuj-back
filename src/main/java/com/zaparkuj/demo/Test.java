package com.zaparkuj.demo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws ParseException {

        final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";

        String oldDateString = "2021-01-01T12:00:00.000+00:00";
        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = sdf.parse(oldDateString);

        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        System.out.println(newDateString);

        sdf.applyPattern(OLD_FORMAT);
        newDateString = sdf.format(d);
        System.out.println(newDateString);


    }
}
