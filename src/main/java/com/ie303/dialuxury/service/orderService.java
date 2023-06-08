package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.order;
import com.ie303.dialuxury.model.orderAggregate;
import com.ie303.dialuxury.model.orderDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface orderService {
    public void createOrderHaveImage(String userId, order orderContainer);
    public void createOrder(String userId, orderDTO orderDTO);
    public void createOrderDetailHaveImage(orderDTO orderDTO);
    public List<orderAggregate> getOrdersWithDetails();
    public List<orderAggregate> getOrdersWithDetailsByUserId(String userId);
    public List<orderAggregate> getOrdersWithDetailsByOrerId(String orderId);

    public order updateImage(String imageName);

    public boolean deleteOrderById(String orderId);
//    public void deleteOrderDetailByOrderId(String orderId);
}
