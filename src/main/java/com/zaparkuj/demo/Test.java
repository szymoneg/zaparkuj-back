package com.zaparkuj.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws ParseException {

        System.out.println(System.currentTimeMillis());

        String myDate = "2020-01-01 12:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeStamp = sdf.parse(myDate);
        System.out.println(timeStamp);

        long miliseconds = timeStamp.getTime();
        System.out.println(miliseconds);


        System.out.println("".isEmpty());
    }
}
