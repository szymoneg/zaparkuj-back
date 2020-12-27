package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.*;
import com.zaparkuj.demo.services.ReservationService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
@EnableScheduling
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

        session.close();

        return reservation;
    }

    @Override
    public ArrayList<Reservation> getAllReservations() {

        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Reservation");
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) query.getResultList();

        session.close();

        return reservations;
    }

    @Override
    public ArrayList<Reservation> getAllActiveReservations() {

        Date dt = new Date();
        Timestamp tstamp = new Timestamp(dt.getTime());

        Session session = factory.openSession();
        session.beginTransaction();

        String hql = "FROM Reservation r WHERE dateEnd < :nowDate AND statusReservation=true";
        Query query = session.createQuery(hql);
        query.setParameter("nowDate", tstamp);
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) query.getResultList();

        session.close();

        return reservations;
    }

    @Override
    public void desactiveReservation(Reservation reservation) {

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        reservation.getPlace().setStatus(true);
        reservation.setStatusReservation(false);

        session.update(reservation);
        session.getTransaction();
        transaction.commit();
        session.close();
    }
}
