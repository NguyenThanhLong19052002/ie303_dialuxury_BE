package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.order;
import com.ie303.dialuxury.model.orderAggregate;
import com.ie303.dialuxury.model.orderDTO;
import com.ie303.dialuxury.service.orderServiceImpl;
import com.ie303.dialuxury.repository.orderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class orderController {
    private final orderServiceImpl orderService;
    private final orderRepository orderRepository;

    @Autowired
    public orderController(orderServiceImpl orderService, orderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<String> createOrder(@PathVariable String userId, @RequestBody orderDTO orderDTO) {
        orderService.createOrder(userId, orderDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<orderAggregate>> getOrdersWithDetailsByUserId(@PathVariable String userId) {
        List<orderAggregate> orderAggregates = orderService.getOrdersWithDetailsByUserId(userId);
        if (orderAggregates.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(orderAggregates);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<orderAggregate>> getOrdersWithDetailsByOrderId(@PathVariable String orderId) {
        List<orderAggregate> orderAggregates = orderService.getOrdersWithDetailsByOrerId(orderId);
        if (orderAggregates.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(orderAggregates);
        }
    }

    @GetMapping("/{orderId}/get")
    public ResponseEntity<order> getOrderById(@PathVariable String orderId) {
        order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
