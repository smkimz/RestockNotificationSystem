package com.hanghae.restocknotificationsystem.notification.productUserNotification.service;

import com.hanghae.restocknotificationsystem.exceptions.ResourceNotFoundException;
import com.hanghae.restocknotificationsystem.notification.productUserNotification.domain.ProductUserNotification;
import com.hanghae.restocknotificationsystem.notification.productUserNotification.repository.ProductUserNotificationRepository;
import com.hanghae.restocknotificationsystem.product.domain.Product;
import com.hanghae.restocknotificationsystem.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductUserNotificationService {

    private final ProductRepository productRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;

    public ProductUserNotificationService(ProductRepository productRepository, ProductUserNotificationRepository productUserNotificationRepository) {
        this.productRepository = productRepository;
        this.productUserNotificationRepository = productUserNotificationRepository;
    }

    @Transactional
    public void subscribe(Long productId, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("## 상품을 찾을 수 없습니다."));

        ProductUserNotification notification = new ProductUserNotification();
        notification.setProduct(product);
        notification.setUserId(userId);
        notification.setActive(true);
        notification.setCreatedDate(LocalDateTime.now());
        notification.setUpdatedDate(LocalDateTime.now());

        productUserNotificationRepository.save(notification);
    }

    @Transactional
    public void unsubscribe(Long productId, Long userId) {
        ProductUserNotification notification = productUserNotificationRepository.findByProductIdAndUserId(productId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("## 구독을 찾을 수 없습니다."));

        notification.setActive(false);
        notification.setUpdatedDate(LocalDateTime.now());

        productUserNotificationRepository.save(notification);
    }

}
