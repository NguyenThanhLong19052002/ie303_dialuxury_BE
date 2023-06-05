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
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Base64;
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

    @PostMapping("/{userId}/createOrder")
    public ResponseEntity<String> createOrderWithNoImage(@PathVariable String userId, @RequestBody orderDTO orderDTO) {
        orderService.createOrderNoImage(userId, orderDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<String> createOrder(@PathVariable String userId, @RequestParam("image") MultipartFile image, @RequestBody orderDTO orderDTO) {
//        String imageData = null;
//        try {
//            imageData = image.getOriginalFilename();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        orderService.createOrder(userId, imageData, orderDTO);
//        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
//        try {
//            String imageName = image.getOriginalFilename();
//            orderService.createOrder(userId, imageName, orderDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to create order", e);
//        }
        if (!image.isEmpty()) {
            // Tiếp tục xử lý tệp đã tải lên
            orderService.createOrder(userId, (String)image.getOriginalFilename(), orderDTO);

            return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
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
