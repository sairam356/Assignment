package com.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.userservice.entities.UserMetadata;
import com.userservice.entities.UserType;
import com.userservice.kafkaevent.UserEvent;
import com.userservice.repo.UserMetaDataRepository;
import com.userservice.repo.UserTypeRepository;

@Service
public class UserEventConsumer {
	@Autowired
	private UserMetaDataRepository userMetadataRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;

	@KafkaListener(topics = "user-events", groupId = "user-events-group")
	public void consumeUserEvent(UserEvent event) {
		switch (event.getEventType()) {
		case "CREATE_META_DATA":
			createMetadata(event);
			break;
		case "SEND_EMAIL":
			sendEmail(event);
			break;
		case "CALL_EXTERNAL_SERVICE":
			callExternalService(event);
			break;
		default:
			System.out.println("Unknown event type: " + event.getEventType());
		}
	}

	private void createMetadata(UserEvent event) {
		UserMetadata metadata = new UserMetadata();
		metadata.setUserId(event.getUserId());
		metadata.setCreateddt(new java.util.Date());
		metadata.setEventStatus("REGISTERED");

		userMetadataRepository.save(metadata);
		System.out.println("Metadata created for user: " + event.getUserId());
	}

	private void sendEmail(UserEvent event) {
		UserType userTypeOptional = userTypeRepository.findByUserType(event.getUserType());
		if (userTypeOptional != null) {
			/*
			 * 
			 * Send EMAIL
			 */
			System.out.println("Email sent to user: " + event.getEmail());
		} else {
			System.out.println("UserType not found for user: " + event.getUserId());
		}
	}

	private void callExternalService(UserEvent event) {
		System.out.println("Calling external service for user: " + event.getUserId());
		// External service logic here
	}
}
