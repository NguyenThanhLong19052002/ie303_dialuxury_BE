package com.ie303.dialuxury.controller;
import com.ie303.dialuxury.model.product;
import com.ie303.dialuxury.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/product")
@CrossOrigin
public class productController {
    @Autowired
    private productService productService;
//    @PostMapping("")
//    public String add(@RequestBody product product){
//        productService.saveproduct(product);
//        return "New product is added";
//    }

    @GetMapping("")
    public List<product> list(){
        return productService.getAllproducts();
    }
}
