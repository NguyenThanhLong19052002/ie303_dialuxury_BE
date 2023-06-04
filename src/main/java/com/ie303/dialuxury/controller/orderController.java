package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.orderDTO;
import com.ie303.dialuxury.service.orderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class orderController {
    private final orderServiceImpl orderService;

    @Autowired
    public orderController(orderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<String> createOrder(@PathVariable String userId, @RequestBody orderDTO orderDTO) {
        orderService.createOrder(userId, orderDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }
}
