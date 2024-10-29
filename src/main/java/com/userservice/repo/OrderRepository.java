package com.userservice.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.userservice.entities.Orders;

import java.util.List;

@Repository
public class OrderRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public OrderRepository(@Qualifier("primaryMongoTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // Save a new Orders to the MongoDB collection
    public Orders saveOrders(Orders Orders) {
        return mongoTemplate.save(Orders);
    }

    // Find an Orders by its ID
    public Orders findOrdersById(String OrdersId) {
        return mongoTemplate.findById(OrdersId, Orders.class);
    }

    // Find all Orderss for a specific user by userId
    public List<Orders> findOrderssByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Orders.class);
    }

    // Update the status of an Orders
    public void updateOrdersStatus(String OrdersId, String status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(OrdersId));
        Orders orders = mongoTemplate.findOne(query, Orders.class);
    
        mongoTemplate.save(orders);
        
    }

    // Delete an Orders by its ID
    public void deleteOrdersById(String OrdersId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(OrdersId));
        mongoTemplate.remove(query, Orders.class);
    }
}

