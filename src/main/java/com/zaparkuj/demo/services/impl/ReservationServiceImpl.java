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
                .addAnnotatedClass(Sector.class)
                .addAnnotatedClass(Place.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Reservation.class)
                .buildSessionFactory();
    }

    @Override
    public Reservation getReservation(int id) {

        Reservation reservation;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Reservation WHERE idReservation=" + id);
            reservation = (Reservation) query.getResultList().get(0);

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return reservation;
    }

    @Override
    public ArrayList<Reservation> getAllReservations() {

        ArrayList<Reservation> reservations;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Reservation");
            reservations = (ArrayList<Reservation>) query.getResultList();

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return reservations;
    }

    @Override
    public ArrayList<Reservation> getAllNowActiveReservations() {

        ArrayList<Reservation> reservations;

        Session session = factory.openSession();
        try {
            Date dt = new Date();
            Timestamp tstamp = new Timestamp(dt.getTime());

            session.beginTransaction();

            String hql = "FROM Reservation r WHERE dateBegin < :nowDate AND dateEnd > :nowDate AND statusReservation=true";
            Query query = session.createQuery(hql);
            query.setParameter("nowDate", tstamp);
            reservations = (ArrayList<Reservation>) query.getResultList();

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return reservations;
    }

    @Override
    public ArrayList<Reservation> getAllNowDesactiveReservations() {

        ArrayList<Reservation> reservations;

        Session session = factory.openSession();
        try {
            Date dt = new Date();
            Timestamp tstamp = new Timestamp(dt.getTime());

            session.beginTransaction();

            String hql = "FROM Reservation r WHERE dateEnd < :nowDate AND statusReservation is :arg";
            Query query = session.createQuery(hql);
            query.setParameter("nowDate", tstamp);
            query.setParameter("arg", true);
            reservations = (ArrayList<Reservation>) query.getResultList();

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return reservations;
    }

    @Override
    public ArrayList<Reservation> getAllReservations(boolean status) {

        ArrayList<Reservation> reservations;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Reservation WHERE statusReservation=:st");
            query.setParameter("st", status);
            reservations = (ArrayList<Reservation>) query.getResultList();

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return reservations;
    }

    @Override
    public ArrayList<Reservation> getUserReservation(int idUser, boolean status) {

        ArrayList<Reservation> reservations = new ArrayList<>();
        Date dt = new Date();
        Timestamp tstamp = new Timestamp(dt.getTime());

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            String str = "FROM Reservation WHERE statusReservation=:status AND car.holderCar.idUser=:id";

            Query query = session.createQuery(str);
            query.setParameter("status", status);
            query.setParameter("id", idUser);
            reservations = (ArrayList<Reservation>) query.getResultList();

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return reservations;
    }

    @Override
    public boolean insertReservation(Reservation reservation) {

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(reservation);
            session.getTransaction().commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean checkPlaceToReservation(int idPlace, Date beginDate, Date endDate) {

        ArrayList<Reservation> reservations = getAllReservations(true);
        Date beginReservation;
        Date endReservation;

        for(Reservation reservation : reservations) {
            if(reservation.getPlace().getIdPlace() != idPlace) {
                continue;
            }
            beginReservation = reservation.getDateBegin();
            endReservation = reservation.getDateEnd();
            if((beginDate.getTime() >= beginReservation.getTime() && beginDate.getTime() <= endReservation.getTime()) ||
                endDate.getTime() >= beginReservation.getTime() && endDate.getTime() <= endReservation.getTime() ||
                    (beginDate.getTime() < beginReservation.getTime() && beginDate.getTime() < endReservation.getTime() &&
                    endDate.getTime() > beginReservation.getTime() && endDate.getTime() > endReservation.getTime())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {

        Session session = factory.openSession();

        try {
            session.beginTransaction();

            session.remove(session.merge(reservation));

            session.getTransaction().commit();
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        finally {
            session.close();
        }
        return true;
    }

    @Override
    public void changeStatus(int id, boolean status) {

        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Reservation reservation = session.get(Reservation.class, id);
            reservation.setStatusReservation(status);
            session.update(reservation);

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
    }
}
