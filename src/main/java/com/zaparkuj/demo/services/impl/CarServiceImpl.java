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

import javax.persistence.NoResultException;
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

        ArrayList<Car> cars;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Car WHERE holderCar.username=:user");
            query.setParameter("user", user);
            cars = (ArrayList<Car>) query.getResultList();
        }
        finally {
            session.close();
        }

        return cars;
    }

    @Override
    public void insertCar(User user, String mark, String model, String licencePlate) {

        Session session = factory.openSession();

        try {
            Car car = new Car(mark, model, licencePlate, user);

            session.beginTransaction();
            session.save(car);
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
    }

    @Override
    public Car selectCarOfLicencePlate(String licencePlate) {

        Car car = null;
        Session session = factory.openSession();

        try {
            Query query = session.createQuery("FROM Car WHERE licencePlate=:plate");
            query.setParameter("plate", licencePlate);
            car = (Car) query.getSingleResult();
        }
        catch (NoResultException exc) {
            return null;
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            session.close();
        }

        return car;
    }

    @Override
    public Car selectCarOfId(int id) {

        Car car = null;
        Session session = factory.openSession();

        try {
            Query query = session.createQuery("FROM Car WHERE idCar=:id");
            query.setParameter("id", id);
            car = (Car) query.getSingleResult();
        }
        catch (NoResultException exc) {
            return null;
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            session.close();
        }

        return car;
    }

    @Override
    public boolean deleteCar(Car car) {

        Session session = factory.openSession();

        try {
            session.beginTransaction();
            System.out.println(car);

            session.remove(session.merge(car));

            session.getTransaction().commit();
            return true;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        finally {
            session.close();
        }
    }
}
