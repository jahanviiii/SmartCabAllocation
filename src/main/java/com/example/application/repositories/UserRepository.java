package com.example.application.repositories;

import com.example.application.module.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUsername(String username);

    List<User> findByUserType(String userType);

    // You can add more custom queries as needed based on your requirements
}

