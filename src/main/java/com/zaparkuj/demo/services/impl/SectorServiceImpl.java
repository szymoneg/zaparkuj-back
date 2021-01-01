package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.Parking;
import com.zaparkuj.demo.entities.Sector;
import com.zaparkuj.demo.services.SectorService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    public SessionFactory factory;

    public SectorServiceImpl() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Parking.class)
                .addAnnotatedClass(Sector.class)
                .buildSessionFactory();
    }

    @Override
    public Sector selectSector(int idSector) {

        Sector sector;
        Session session = factory.openSession();

        try {
            session.beginTransaction();
            sector = session.get(Sector.class, idSector);
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return sector;
    }

    @Override
    public ArrayList<Sector> selectSectorsOnParking(int idParking) {

        ArrayList<Sector> sectors;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Sector WHERE parking.idParking=:id");
            query.setParameter("id", idParking);
            sectors = (ArrayList<Sector>) query.getResultList();

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

        return sectors;
    }
}
