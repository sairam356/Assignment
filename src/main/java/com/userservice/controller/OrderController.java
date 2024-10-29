package com.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.userservice.entities.Orders;
import com.userservice.repo.OrderRepository;
import com.userservice.requests.OrderRequest;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public Orders createOrder(@RequestBody OrderRequest request) {
        // Get the currently authenticated user's email
        String email = getCurrentUserEmail();
        
        /*
         * 
         *  we use mapper  builder  pattern issue with editor not using 
         */

        Orders order = new Orders();
        // Set order details
        order.setUserId(email); 
        order.setItems(request.getItems());
        order.setTotalAmount(request.getAmount());
        
       
        return orderRepository.saveOrders(order);
    }

    @GetMapping("/myorders")
    public List<Orders> getUserOrders() {
        // Get the currently authenticated user's email
        String email = getCurrentUserEmail();

        // Retrieve orders for the user
        return orderRepository.findOrderssByUserId(email);
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();  // Email is stored as the username
        } else {
            return principal.toString();
        }
    }
}
