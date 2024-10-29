package com.userservice.requests;

import java.util.List;

public class OrderRequest {
	
	
	private String amount;
	
	private List<String> items;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	} 
	
	
	

}
