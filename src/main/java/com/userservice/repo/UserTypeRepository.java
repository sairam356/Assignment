package com.userservice.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.userservice.entities.UserType;

@Repository
public class UserTypeRepository {

	private final MongoTemplate secondaryMongoTemplate;

	@Autowired
	public UserTypeRepository(@Qualifier("secondaryMongoTemplate") MongoTemplate secondaryMongoTemplate) {
		this.secondaryMongoTemplate = secondaryMongoTemplate;
	}

	public void saveMetadata(UserType metadata) {
		secondaryMongoTemplate.save(metadata);
	}

	public UserType findByUserId(String userId) {
		return secondaryMongoTemplate.findById(userId, UserType.class);
	}

	public UserType findByUserType(String userType) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userType").is(userType));

		UserType userTypeObj = secondaryMongoTemplate.findOne(query, UserType.class);

		return userTypeObj;
	}

}
