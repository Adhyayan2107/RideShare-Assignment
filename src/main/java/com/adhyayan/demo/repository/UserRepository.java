package com.adhyayan.demo.repository;

import com.adhyayan.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User , String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
