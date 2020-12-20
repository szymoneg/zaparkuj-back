package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.dto.PlaceDTO;
import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.entities.Place;
import com.zaparkuj.demo.services.PlaceService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

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
        System.out.println(query.getResultList());
        System.out.println(id);
        ArrayList<Place> places = (ArrayList<Place>) query.getResultList();
        System.out.println(places);

        session.getTransaction().commit();
        session.close();

        return places;
    }
}
