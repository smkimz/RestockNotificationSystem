package com.hanghae.restocknotificationsystem.product.repository;

import com.hanghae.restocknotificationsystem.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
