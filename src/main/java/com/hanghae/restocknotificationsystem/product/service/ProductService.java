package com.hanghae.restocknotificationsystem.product.service;

import com.hanghae.restocknotificationsystem.product.domain.Product;
import com.hanghae.restocknotificationsystem.product.dto.ProductRequest;
import com.hanghae.restocknotificationsystem.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void registerProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setStockStatus(request.getStockStatus());
        product.setRestockCount(0);
        productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
