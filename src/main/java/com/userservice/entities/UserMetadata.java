package com.userservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "user_metadata")
public class UserMetadata {
    @Id
    private String id;
    private String userId;
    private Date createddt;
    private String eventStatus;
    

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateddt() {
		return createddt;
	}
	public void setCreateddt(Date createddt) {
		this.createddt = createddt;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

    
}