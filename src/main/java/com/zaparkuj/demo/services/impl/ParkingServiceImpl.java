package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.services.ParkingService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private SessionFactory factory;

    public ParkingServiceImpl() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Parking.class)
                .buildSessionFactory();
    }

    @Override
    public ArrayList<Parking> selectAllParkings() {

        ArrayList<Parking> parkings;
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Parking");
            parkings = (ArrayList<Parking>) query.getResultList();
        }
        finally {
            session.close();
        }

        return parkings;
    }

    @Override
    public Parking selectParking(int id) {

        Parking parking;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            parking = session.get(Parking.class, id);
        }
        finally {
            session.close();
        }

        return parking;
    }
}
