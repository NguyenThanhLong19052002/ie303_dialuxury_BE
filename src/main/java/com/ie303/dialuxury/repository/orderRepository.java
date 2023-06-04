package com.ie303.dialuxury.repository;

import com.ie303.dialuxury.model.order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface orderRepository extends MongoRepository<order, String> {
//    order findByIdAndStatus(String _id, String status);
//    List<order> findByStatus(String status);
//    order findBy_Id(String _id);
//    List<order> findByUserId(String userId);
//    order findFirstByOrderByOrderNumberDesc();
}
