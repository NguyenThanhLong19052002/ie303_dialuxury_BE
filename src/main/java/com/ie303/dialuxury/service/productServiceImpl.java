package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.product;
import com.ie303.dialuxury.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productServiceImpl implements productService {
    @Autowired
    private productRepository productRepository;

    @Override
    public product saveproduct(product product) {
        return productRepository.save(product);
    }

    @Override
    public List<product> getAllproducts() {
        return productRepository.findAll();
    }
}
