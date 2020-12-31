package com.zaparkuj.demo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws ParseException {

        System.out.println(System.currentTimeMillis());

        /*String myDate = "2020-01-01 12:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeStamp = sdf.parse(myDate);
        System.out.println(timeStamp);

        long miliseconds = timeStamp.getTime();
        System.out.println(miliseconds);*/

        Date dateBegin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2030-02-16T19:38:40.000+00:00");
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2031-02-16T19:38:40.000+00:00");

        System.out.println(dateBegin.getTime() < dateEnd.getTime());

    }
}
