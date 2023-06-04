package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.orderDTO;

public interface orderService {
    void createOrder(String userId, orderDTO orderDTO);
}
