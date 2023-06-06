package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.cart;
import com.ie303.dialuxury.model.order;
import com.ie303.dialuxury.model.orderDTO;
import com.ie303.dialuxury.model.orderAggregate;
import com.ie303.dialuxury.service.orderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ie303.dialuxury.repository.orderRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/user/{userId}/order")
    public List<order> getOrdersByUserId(@PathVariable String userId) {
        return orderRepository.findByUserId(userId);
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

    @PostMapping("/{userId}/createOrder")
    public ResponseEntity<String> createOrderWithNoImage(@PathVariable String userId, @RequestBody orderDTO orderDTO) {
        orderService.createOrder(userId, orderDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/createOrderDetail")
    public ResponseEntity<String> createOrderDetailHaveImage(@RequestBody orderDTO orderDTO) {
        orderDTO orderDTOContianer = new orderDTO();
        orderDTOContianer.setCart(orderDTO.getCart());
        orderService.createOrderDetailHaveImage(orderDTOContianer);
        return new ResponseEntity<>("OrderDetail created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<String> createOrder(@PathVariable String userId,
                                              @RequestParam("image") MultipartFile image,
                                              @RequestParam("shippingAddress") String shippingAddress,
                                              @RequestParam("paymentMethod") String paymentMethod,
                                              @RequestParam("total") long total) {
        order order = new order();

        if (image != null && !image.isEmpty()) {
            try {
                // Tạo tên file duy nhất
                String originalFileName = image.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileExtension;

                // Lưu hình ảnh vào ổ đĩa
                String directoryPath = "src/PaymentImages/";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
                }
                File file = new File(directory, fileName);
                image.transferTo(file);

                //Set dữ liệu cho orderDTO
//                orderDTO.setCart(cart);
                order.setImage(fileName);
                order.setShippingAddress(shippingAddress);
                order.setPaymentMethod(paymentMethod);
                order.setTotalPriceOrder(total);

            } catch (IOException e) {
                // Xử lý lỗi khi không thể lưu hình ảnh
                e.printStackTrace();
            }
        }

        // Lưu đơn hàng vào cơ sở dữ liệu
        orderService.createOrderHaveImage(userId, order);
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/updateStatus")
    public order updateOrderStatus(@PathVariable("orderId") String orderId, @RequestBody String status) {
        order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

}
