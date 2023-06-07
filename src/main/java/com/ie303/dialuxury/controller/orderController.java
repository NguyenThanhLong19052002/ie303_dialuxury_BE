package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.cart;
import com.ie303.dialuxury.model.order;
import com.ie303.dialuxury.model.orderDTO;
import com.ie303.dialuxury.model.orderAggregate;
import com.ie303.dialuxury.service.orderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ie303.dialuxury.repository.orderRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping
    public ResponseEntity<List<orderAggregate>> getAllOrdersWithDetails() {
        List<orderAggregate> orderAggregates = orderService.getOrdersWithDetails();
        if (orderAggregates.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(orderAggregates);
        }
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

    @GetMapping("/{orderId}/getImage")
    public ResponseEntity<Resource> getImageByOrderId(@PathVariable String orderId) {
        order order = orderRepository.findById(orderId).orElse(null);
        if (order != null && order.getImage() != null) {
            String imagePath = "src/PaymentImages/" + order.getImage();
            Path imageFilePath = Paths.get(imagePath);
            if (Files.exists(imageFilePath)){
                Resource imageResource = new FileSystemResource(imageFilePath.toFile());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return ResponseEntity.ok().headers(headers).body(imageResource);
            }
        }
        return ResponseEntity.notFound().build();
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
    public order updateOrderStatus(@PathVariable("orderId") String orderId, @RequestBody order orderData) {
        order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(orderData.getStatus());
            return orderRepository.save(order);
        }
        return null;
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId) {
        boolean isDeleted = orderService.deleteOrderById(orderId);
        if (isDeleted) {
            return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }

}
