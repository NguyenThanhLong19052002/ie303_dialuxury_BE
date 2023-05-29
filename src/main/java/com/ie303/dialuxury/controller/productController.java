package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.product;
import com.ie303.dialuxury.repository.productRepository;
import com.ie303.dialuxury.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class productController {
    @Autowired
    private productService productService;

    @Autowired
    private productRepository productRepository;

    //    @PostMapping("")
//    public String add(@RequestBody product product){
//        productService.saveproduct(product);
//        return "New product is added";
//    }
    @GetMapping("")
    public List<product> showProduct() {
        return productRepository.findAll();
    }

    @GetMapping("/{productId}")
    public product getProductById(@PathVariable String productId) {
        Optional<product> productOptional = productRepository.findById(productId);
        return productOptional.orElse(null); // Hoặc xử lý theo ý đồ của bạn khi không tìm thấy sản phẩm
    }

    @GetMapping("/search")

    public List<product> searchProduct(@RequestParam("name") String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }


}
