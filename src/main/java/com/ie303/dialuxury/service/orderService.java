package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.orderAggregate;
import com.ie303.dialuxury.model.orderDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface orderService {
    void createOrderNoImage(String userId, orderDTO orderDTO);
    void createOrder(String userId, String image, orderDTO orderDTO);
    List<orderAggregate> getOrdersWithDetailsByUserId(String userId);
    List<orderAggregate> getOrdersWithDetailsByOrerId(String orderId);

}
