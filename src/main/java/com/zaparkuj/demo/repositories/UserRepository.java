package com.zaparkuj.demo.repositories;

import com.zaparkuj.demo.dto.EditUserRequest;
import com.zaparkuj.demo.dto.EditUserResponse;
import com.zaparkuj.demo.entities.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByEmail(String email);
    User findUserByPasswordAndEmail(String email, String username);
}
