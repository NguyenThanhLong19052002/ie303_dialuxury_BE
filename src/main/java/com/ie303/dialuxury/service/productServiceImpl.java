package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.product;
import com.ie303.dialuxury.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class productServiceImpl implements productService {
    @Autowired
    private productRepository productRepository;

    //add product:

    public product addProduct(product product) {
        return productRepository.save(product);
    }

    //get all products:

    public List<product> getAllProducts() {
        return productRepository.findAll();
    }

    //get by id:
    public Optional<product> getProductById(String productid){
        return productRepository.findById(productid);
    }
    //update product:
    public product updateProduct(String productid, product updatedProduct) {
        Optional<product> existingProduct = productRepository.findById(productid);
        if (existingProduct.isPresent()) {
            product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            return productRepository.save(product);
        } else {
            return null;
        }
    }
    //delete product:
    public void deleteProduct(String productid) {
        productRepository.deleteById(productid);
    }
}
