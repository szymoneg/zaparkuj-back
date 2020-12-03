package com.zaparkuj.demo.repositories;

import com.zaparkuj.demo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    ArrayList<User> findByUsername(String username);
}
