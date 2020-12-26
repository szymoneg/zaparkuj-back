package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.entities.Place;
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
                .addAnnotatedClass(Place.class)
                .buildSessionFactory();
    }

    @Override
    public ArrayList<Place> selectPlaces(int id) {

        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Place WHERE parking.idparking=" + id);  // pobranie wszystkich miejsc parkingowych o danym id
        ArrayList<Place> places = (ArrayList<Place>) query.getResultList();

        session.getTransaction().commit();
        session.close();

        return places;
    }

    @Override
    public long selectCountPlaces(int id, boolean status) {

        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT count(idPlace) FROM Place WHERE parking.idparking=" + id + " AND status=" + status);
        System.out.println(query.getResultList().get(0));
        Long countPlaces = (Long) query.getResultList().get(0);

        session.close();

        return countPlaces;
    }
}
