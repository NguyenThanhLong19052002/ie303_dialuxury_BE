package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.user;
import com.ie303.dialuxury.model.order;
import com.ie303.dialuxury.repository.userRepository;
import com.ie303.dialuxury.repository.orderRepository;
import com.ie303.dialuxury.repository.productRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class adminController {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private productRepository productRepository;

    //    lấy danh sách user
    @GetMapping("/user")
    public ResponseEntity<List> ShowUser() {
        List<user> user = userRepository.findAll();
        return ResponseEntity.ok(user);
    }

    //    lấy ra hóa đơn có tình trạng là "chưa xử lý"
//    @GetMapping("/order/unprocessed")
//    public List<order> getUnprocessedOrders() {
//        return orderRepository.findByStatus("chưa xử lý");
//    }

}
