package com.ie303.dialuxury.repository;

import com.ie303.dialuxury.model.orderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderDetailRepository extends MongoRepository<orderDetail, String> {
    void deleteByOrderId(String orderId);

}
