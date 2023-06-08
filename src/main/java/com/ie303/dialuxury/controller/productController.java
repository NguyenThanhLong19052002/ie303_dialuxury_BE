package com.ie303.dialuxury.controller;
import com.ie303.dialuxury.model.product;
import com.ie303.dialuxury.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/product")
@CrossOrigin
public class productController {
    @Autowired
    private productService productService;
    @PostMapping("")
    public ResponseEntity<product> addProduct(@RequestBody product product) {
        product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<product>> getAllProducts() {
        List<product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // category products:
//    @GetMapping("/category/{category}")
//    public ResponseEntity<List<product>> getProductsByCategory(@PathVariable String category) {
//        List<product> products = productService.getProductsByCategory(category);
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
    @GetMapping("/category/{category}")
    public List<product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/{productid}")
    public ResponseEntity<product> getProductById(@PathVariable("productid") String productid) {
        Optional<product> product = productService.getProductById(productid);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{productid}")
    public ResponseEntity<product> updateProduct(@PathVariable("productid") String productid, @RequestBody product product) {
        product updatedProduct = productService.updateProduct(productid, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{productid}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("productid") String productid) {
        productService.deleteProduct(productid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<product>> searchProductsByName(@RequestParam String name) {
        List<product> products = productService.searchProductsByName(name);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }


    //get new products:
    @GetMapping("/new")
    public List<product> getNewProducts() {
        // Lấy 4 sản phẩm mới nhất
        List<product> allProducts = productService.getNewProducts();
        int numProductsToShow = 4;
        int startIndex = Math.max(0, allProducts.size() - numProductsToShow);
        int endIndex = allProducts.size();
        return allProducts.subList(startIndex, endIndex);
    }

}
