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

        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Parking");
        ArrayList<Parking> parkings = (ArrayList<Parking>) query.getResultList();

        session.close();

        return parkings;
    }

    @Override
    public Parking selectParking(int id) {

        Session session = factory.openSession();
        session.beginTransaction();

        Parking parking = session.get(Parking.class, id);

        session.close();

        return parking;
    }
}
