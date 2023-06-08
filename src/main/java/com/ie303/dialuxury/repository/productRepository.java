package com.ie303.dialuxury.repository;
import com.ie303.dialuxury.model.product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productRepository extends MongoRepository<product, String> {

    List<product> findAllByCategory(String category);
    List<product> findByCategory(String category);
    List<product> findByNameContainingIgnoreCase(String name);
}
