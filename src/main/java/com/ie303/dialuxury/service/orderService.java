package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.orderAggregate;
import com.ie303.dialuxury.model.orderDTO;

import java.util.List;

public interface orderService {
    void createOrder(String userId, orderDTO orderDTO);
    List<orderAggregate> getOrdersWithDetailsByUserId(String userId);
    List<orderAggregate> getOrdersWithDetailsByOrerId(String orderId);

}
