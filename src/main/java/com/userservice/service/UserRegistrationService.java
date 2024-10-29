package com.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.userservice.entities.User;
import com.userservice.kafkaevent.UserEvent;
import com.userservice.repo.UserRepository;

@Service
public class UserRegistrationService {

	  private static final String TOPIC = "user-events";
	  
		 private final MongoTemplate primaryMongoTemplate;

		    @Autowired
		    public UserRegistrationService(@Qualifier("primaryMongoTemplate") MongoTemplate primaryMongoTemplate) {
		        this.primaryMongoTemplate = primaryMongoTemplate;
		    }

	    @Autowired
	    private KafkaTemplate<String, Object> kafkaTemplate;

	    public User registerUser(User user) {
	        // Save user in primary MongoDB
	    	User userobj = primaryMongoTemplate.save(user);

	        // Produce Kafka events for different actions
	        kafkaTemplate.send(TOPIC, createUserEvent(userobj, "CREATE_META_DATA"));
	        kafkaTemplate.send(TOPIC, createUserEvent(userobj, "SEND_EMAIL"));
	        kafkaTemplate.send(TOPIC, createUserEvent(userobj, "CALL_EXTERNAL_SERVICE"));
			return userobj;
	    }

	    private UserEvent createUserEvent(User user, String eventType) {
	        UserEvent event = new UserEvent();
	        event.setUserType(user.getUserType());
	        event.setEmail(user.getEmail());
	        event.setEventType(eventType);
	        return event;
	    }
}
