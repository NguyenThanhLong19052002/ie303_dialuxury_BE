package com.ie303.dialuxury.service;
import com.ie303.dialuxury.model.*;
import com.ie303.dialuxury.repository.orderRepository;
import com.ie303.dialuxury.repository.orderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class orderServiceImpl implements orderService {
    private final MongoTemplate mongoTemplate;
    private final orderRepository orderRepository;
    private final orderDetailRepository orderDetailRepository;
    private order order = new order();
    private orderDetail orderDetail = new orderDetail();

    @Autowired
    public orderServiceImpl(MongoTemplate mongoTemplate, orderRepository orderRepository, orderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createOrderHaveImage(String userId, order orderContainer){
        // Đếm số lượng hoá đơn hiện có
        long orderCount = orderRepository.count();
        // Tạo mã định danh cho hoá đơn mới
        String orderNumber = "HD" + String.format("%02d", orderCount + 1);
        order.setId(orderNumber);
        order.setUserId(userId);
        order.setImage(orderContainer.getImage());
        order.setCreatedAt(new Date());
        order.setStatus("Đang xử lý");
        order.setShippingAddress(orderContainer.getShippingAddress());
        order.setPaymentMethod(orderContainer.getPaymentMethod());
        order.setTotalPriceOrder(orderContainer.getTotalPriceOrder());
        orderRepository.save(order);

    }

    @Override
    public void createOrderDetailHaveImage(orderDTO orderDTO){
        long orderCount = orderRepository.count();
        // Tạo mã định danh cho hoá đơn mới nhất
        String orderNumber = "HD" + String.format("%02d", orderCount);
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
        order.setTotalPriceOrder(orderDTO.getTotal());
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

    @Override
    public order updateImage(String imageName){
        order lastOrder = orderRepository.findTopByOrderByCreatedAtDesc();
        if(lastOrder != null){
            lastOrder.setImage(imageName);
            return orderRepository.save(lastOrder);
        }
        return null;
    }

    @Override
    public List<orderAggregate> getOrdersWithDetailsByUserId(String userId){
        return getOrdersWithDetails_UserId(userId);
    }

    @Override
    public List<orderAggregate> getOrdersWithDetailsByOrerId(String orderId){
        return getOrdersWithDetails_OrderId(orderId);
    }

    @Override
    public List<orderAggregate> getOrdersWithDetails(){
        return getOrdersWithDetailsPrivate();
    }

    private List<orderAggregate> getOrdersWithDetailsPrivate() {
        TypedAggregation<order> aggregation = Aggregation.newAggregation(order.class,
                Aggregation.lookup("orderDetail", "_id", "orderId", "orderDetails"),
                Aggregation.unwind("orderDetails"),
                Aggregation.project("_id", "userId", "status", "shippingAddress", "paymentMethod", "totalPriceOrder", "createdAt")
                        .and("orderDetails.quantity").as("quantity")
                        .and("orderDetails.totalPrice").as("totalPrice")
                        .and("orderDetails.product").as("product")
                        .and("orderDetails._id").as("orderDetailId")
        );

        return mongoTemplate.aggregate(aggregation, "order", orderAggregate.class).getMappedResults();
    }

    @Override
    public boolean deleteOrderById(String orderId){
        order order = orderRepository.findById(orderId).orElse(null);
        if(order != null){
            orderRepository.deleteById(orderId);
            orderDetailRepository.deleteByOrderId(orderId);
            return true;
        }
        return false;
    }

    private List<orderAggregate> getOrdersWithDetails_UserId(String userId){
        TypedAggregation<order> aggregation = Aggregation.newAggregation(order.class,
                Aggregation.match(Criteria.where("userId").is(userId)),
                Aggregation.lookup("orderDetail", "_id", "orderId", "orderDetails"),
                Aggregation.unwind("orderDetails"),
                Aggregation.project("_id", "userId", "status", "shippingAddress", "paymentMethod", "totalPriceOrder", "createdAt")
                        .and("orderDetails.quantity").as("quantity")
                        .and("orderDetails.totalPrice").as("totalPrice")
                        .and("orderDetails.product").as("product")
                        .and("orderDetails._id").as("orderDetailId")
//                        .andExclude()
        );

        return mongoTemplate.aggregate(aggregation, "order", orderAggregate.class).getMappedResults();
    }
    private List<orderAggregate> getOrdersWithDetails_OrderId(String orderId){
        TypedAggregation<order> aggregation = Aggregation.newAggregation(order.class,
                Aggregation.match(Criteria.where("_id").is(orderId)),
                Aggregation.lookup("orderDetail", "_id", "orderId", "orderDetails"),
                Aggregation.unwind("orderDetails"),
                Aggregation.project("_id", "userId", "status", "shippingAddress", "paymentMethod", "totalPriceOrder", "createdAt")
                        .and("orderDetails.quantity").as("quantity")
                        .and("orderDetails.totalPrice").as("totalPrice")
                        .and("orderDetails.product").as("product")
                        .and("orderDetails._id").as("orderDetailId")
//                        .andExclude()
        );

        return mongoTemplate.aggregate(aggregation, "order", orderAggregate.class).getMappedResults();
    }
}
