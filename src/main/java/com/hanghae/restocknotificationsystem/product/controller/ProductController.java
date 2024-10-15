package com.hanghae.restocknotificationsystem.product.controller;

import com.hanghae.restocknotificationsystem.product.domain.Product;
import com.hanghae.restocknotificationsystem.product.dto.ProductRequest;
import com.hanghae.restocknotificationsystem.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerProduct(@RequestBody ProductRequest request) {
        productService.registerProduct(request);
        return ResponseEntity.ok("## 상품 등록이 완료되었습니다.");
    }
}
