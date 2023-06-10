package com.ie303.dialuxury.service;
import com.ie303.dialuxury.model.product;

import java.util.List;
import java.util.Optional;
public interface productService {
    public product addProduct(product product);
    public List<product> getAllProducts();
    List<product> getProductsByCategory(String category);
    public Optional<product> getProductById(String productid);
    public product updateProduct(String productid, product updatedProduct);
    public void deleteProduct(String productid);

    public List<product> searchProductsByName(String name);

    public List<product> getNewProducts();
    List<product> getAllProductsSortedByQuantitySold();

}
