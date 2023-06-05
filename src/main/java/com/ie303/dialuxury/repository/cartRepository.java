package com.ie303.dialuxury.repository;

import com.ie303.dialuxury.model.cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface cartRepository extends MongoRepository<cart, String> {

}
