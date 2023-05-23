package com.ie303.dialuxury.service;
import com.ie303.dialuxury.model.product;

import java.util.List;
public interface productService {
    public product saveproduct(product product);
    public List<product> getAllproducts();
}
