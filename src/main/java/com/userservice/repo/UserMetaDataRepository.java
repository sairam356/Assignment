package com.userservice.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.userservice.entities.UserMetadata;

@Repository
public class UserMetaDataRepository {

    private final MongoTemplate secondaryMongoTemplate;

    @Autowired
    public UserMetaDataRepository(@Qualifier("secondaryMongoTemplate") MongoTemplate secondaryMongoTemplate) {
        this.secondaryMongoTemplate = secondaryMongoTemplate;
    }

    // Custom methods for working with user metadata can go here

    public void saveMetadata(UserMetadata metadata) {
        secondaryMongoTemplate.save(metadata);
    }

    // Query methods for metadata
    public UserMetadata findByUserId(String userId) {
        return secondaryMongoTemplate.findById(userId, UserMetadata.class);
    }

	public void save(UserMetadata metadata) {
		// TODO Auto-generated method stub
		 secondaryMongoTemplate.save(UserMetadata.class);
	}
}
