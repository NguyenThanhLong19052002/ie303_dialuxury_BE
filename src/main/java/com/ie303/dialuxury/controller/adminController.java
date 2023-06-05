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
    public List<user> ShowUser() {
        return userRepository.findByRoleOrRoleIsNull("user");
    }

    //    lấy ra hóa đơn có tình trạng là "chưa xử lý"
//    @GetMapping("/order/unprocessed")
//    public List<order> getUnprocessedOrders() {
//        return orderRepository.findByStatus("chưa xử lý");
//    }

    //    Đếm số lượng user
    @GetMapping("/user/count")
    public int CountUsersByRole() {
        return userRepository.countByRoleOrRoleIsNull("user");
    }

    //    đếm số lượng order
    @GetMapping("/order/count")
    public long CountOrder() {
        return orderRepository.count();
    }

    @GetMapping("/product/count")
    public long CountProduct() {
        return productRepository.count();
    }

    @GetMapping("/revenue")
    public long CalculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .mapToLong(order::getTotalPriceOrder)
                .sum();
    }

}
