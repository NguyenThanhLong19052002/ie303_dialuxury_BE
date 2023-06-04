package com.ie303.dialuxury.service;
import com.ie303.dialuxury.model.cart;
import com.ie303.dialuxury.model.orderDTO;
import com.ie303.dialuxury.model.order;
import com.ie303.dialuxury.model.orderDetail;
import com.ie303.dialuxury.repository.orderRepository;
import com.ie303.dialuxury.repository.orderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class orderServiceImpl implements orderService {
    private final orderRepository orderRepository;
    private final orderDetailRepository orderDetailRepository;
    private order order = new order();
    private orderDetail orderDetail = new orderDetail();

    @Autowired
    public orderServiceImpl(orderRepository orderRepository, orderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public void createOrder(String userId, orderDTO orderDTO){
        // Đếm số lượng hoá đơn hiện có
        long orderCount = orderRepository.count();
        // Tạo mã định danh cho hoá đơn mới
        String orderNumber = "HD" + String.format("%02d", orderCount + 1);
        order.setId(orderNumber);
        order.setUserId(userId);
        order.setImage(orderDTO.getImage());
        order.setCreatedAt(new Date());
        order.setStatus("Đang xử lý");
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setTotalPrice(orderDTO.getTotal());
        orderRepository.save(order);

        //set dữ liệu cho orderDetail
        for(cart item : orderDTO.getCart()){
            // Đếm số lượng chi tiết hoá đơn hiện có
            long orderDetailCount = orderDetailRepository.count();
            // Tạo mã định danh cho chi tiết hoá đơn mới
            String orderDetailNumber = "CTHD" + String.format("%02d", orderDetailCount + 1);
            orderDetail.setId(orderDetailNumber);
            orderDetail.setOrderId(orderNumber);
            orderDetail.setProduct(item.getProduct());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTotalPrice(item.getTotalPrice());
            orderDetailRepository.save(orderDetail);
        }
    }
}
