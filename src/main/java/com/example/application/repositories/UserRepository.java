package com.example.application.repositories;

import com.example.application.module.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Component
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
    List<User> findByUserType(String userType);

}

