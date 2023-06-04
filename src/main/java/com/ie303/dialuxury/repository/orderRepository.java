package com.ie303.dialuxury.repository;

import com.ie303.dialuxury.model.order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface orderRepository extends MongoRepository<order, String> {
    order findByMahdAndTinhtrang(String mahd, String tinhtrang);
    List<order> findByTinhtrang(String tinhtrang);
    order findByMahd(String mahd);
    List<order> findByUserId(String userId);
}
