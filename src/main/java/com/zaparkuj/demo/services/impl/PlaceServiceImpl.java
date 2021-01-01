package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.entities.Place;
import com.zaparkuj.demo.entities.Sector;
import com.zaparkuj.demo.services.PlaceService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    public SessionFactory factory;

    public PlaceServiceImpl() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Parking.class)
                .addAnnotatedClass(Sector.class)
                .addAnnotatedClass(Place.class)
                .buildSessionFactory();
    }

    @Override
    public ArrayList<Place> selectPlaces(int id) {

        ArrayList<Place> places;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Place WHERE sector.idSector=" + id);  // pobranie wszystkich miejsc parkingowych o danym id
            places = (ArrayList<Place>) query.getResultList();

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return places;
    }

    @Override
    public long selectCountPlaces(int id, boolean status) {

        long countPlaces = 0;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("SELECT count(idPlace) FROM Place WHERE sector.idSector=" + id + " AND status=" + status);
            countPlaces = (Long) query.getResultList().get(0);
        }
        finally {
            session.close();
        }

        return countPlaces;
    }

    @Override
    public Place selectPlace(int id) {

        Place place;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            place = session.get(Place.class, id);

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return place;
    }
}
