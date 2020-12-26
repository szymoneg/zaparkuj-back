package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.*;
import com.zaparkuj.demo.services.ReservationService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private SessionFactory factory;

    public ReservationServiceImpl() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Parking.class)
                .addAnnotatedClass(Place.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Reservation.class)
                .buildSessionFactory();
    }

    @Override
    public Reservation getReservation(int id) {

        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Reservation WHERE idReservation=" + id);
        Reservation reservation = (Reservation) query.getResultList().get(0);

        return reservation;
    }

    @Override
    public ArrayList<Reservation> getAllReservations() {

        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Reservation");
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) query.getResultList();

        return reservations;
    }
}
