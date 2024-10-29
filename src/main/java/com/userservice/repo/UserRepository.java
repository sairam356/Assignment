package com.userservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.userservice.entities.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
  
    
    Optional<User> findByEmail(String email);
    
}