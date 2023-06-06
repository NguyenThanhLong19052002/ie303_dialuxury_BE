package com.ie303.dialuxury.repository;

import com.ie303.dialuxury.model.order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

@Repository
public interface orderRepository extends MongoRepository<order, String> {
//    order findByIdAndStatus(String _id, String status);
//    List<order> findByStatus(String status);
//    order findBy_Id(String _id);
    List<order> findByUserId(String userId);
    Optional<order> findById(String orderId);

    order findTopByOrderByCreatedAtDesc();
}
