package com.zaparkuj.demo.services.impl;

import com.zaparkuj.demo.dto.UserDTO;
import com.zaparkuj.demo.entities.Sector;
import com.zaparkuj.demo.entities.User;
import com.zaparkuj.demo.repositories.UserRepository;
import com.zaparkuj.demo.services.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionFactory factory;

    public UserServiceImpl() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Sector.class)
                .buildSessionFactory();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null){
            throw new UsernameNotFoundException("User not found wtih username: " + s);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public User save(UserDTO user) {
         User newUser = new User();
         newUser.setUsername(user.getUsername());
         newUser.setEmail(user.getEmail());
         newUser.setPassword(passwordEncoder.encode(user.getPassword()));
         return userRepository.save(newUser);
    }

    @Override
    public boolean saveFullDataUser(User user) {

        Session session = factory.openSession();
        try {
            session.beginTransaction();

            session.update(user);

            session.getTransaction().commit();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            session.close();
        }

        return true;
    }

    @Override
    public User findUserByEmail(String email) {

        User user = null;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM User WHERE email=:email");
            query.setParameter("email", email);
            user = (User) query.getSingleResult();
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

        return user;
    }

    @Override
    public User findUserByUsername(String username) {

        User user = null;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM User WHERE username=:username");
            query.setParameter("username", username);
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public void changePassword(String userName, String newPassword) {

        User user = null;
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            user = findUserByUsername(userName);
            user.setPassword(passwordEncoder.encode(newPassword));
            session.update(user);

            session.getTransaction().commit();
        }
        finally {
            session.close();
        }

    }
}
