package com.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userservice.entities.CustomUserDetails;
import com.userservice.entities.User;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	 private final MongoTemplate primaryMongoTemplate;

	    @Autowired
	    public CustomUserDetailsService(@Qualifier("primaryMongoTemplate") MongoTemplate primaryMongoTemplate) {
	        this.primaryMongoTemplate = primaryMongoTemplate;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        Query query = new Query();
	        query.addCriteria(Criteria.where("email").is(email));
	        
	        User user = primaryMongoTemplate.findOne(query, User.class);
	        
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	        }
	        
	        // Implement MyUserDetails as a class that implements UserDetails
	        return new CustomUserDetails(user);
	    }
}