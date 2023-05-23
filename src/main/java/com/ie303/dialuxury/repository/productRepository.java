package com.ie303.dialuxury.repository;
import com.ie303.dialuxury.model.product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository extends MongoRepository<product, String> {
}
