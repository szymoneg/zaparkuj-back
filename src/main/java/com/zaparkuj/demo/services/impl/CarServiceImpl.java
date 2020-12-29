package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.entities.Car;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.services.CarService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    private SessionFactory factory;

    public CarServiceImpl() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Car.class)
                .buildSessionFactory();
    }

    /* ---- Funkcja zwracająca liste samochodów użytkownika ---- */
    @Override
    public ArrayList<Car> selectCarsUser(String user) {

        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Car WHERE holderCar.username=:user");
        query.setParameter("user", user);
        ArrayList<Car> cars = (ArrayList<Car>) query.getResultList();

        return cars;
    }
}
