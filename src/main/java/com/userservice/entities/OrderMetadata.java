package com.userservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_order_metadata")
public class OrderMetadata {
	
	@Id
    private String id;
	
	private String user_order_id;
	
	private String orderNotes;
	
	private String orderType;
	
	private String specialFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_order_id() {
		return user_order_id;
	}

	public void setUser_order_id(String user_order_id) {
		this.user_order_id = user_order_id;
	}

	public String getOrderNotes() {
		return orderNotes;
	}

	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSpecialFlag() {
		return specialFlag;
	}

	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}
	
	
	
	

}
