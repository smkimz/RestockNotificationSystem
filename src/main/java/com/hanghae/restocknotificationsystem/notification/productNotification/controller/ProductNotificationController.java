package com.hanghae.restocknotificationsystem.notification.productNotification.controller;

import com.hanghae.restocknotificationsystem.notification.productNotification.service.ProductNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProductNotificationController {

    private final ProductNotificationService productNotificationService;

    public ProductNotificationController(ProductNotificationService productNotificationService) {
        this.productNotificationService = productNotificationService;
    }

    @PostMapping("/products/{productId}/notifications/re-stock")
    public ResponseEntity<String> sendRestockNotification(@PathVariable Long productId) {
        productNotificationService.sendRestockNotification(productId);
        return ResponseEntity.ok("## 재입고 알림이 발송되었습니다.");
    }

    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public ResponseEntity<String> manualRestockNotification(@PathVariable Long productId) {
        productNotificationService.manualRestockNotification(productId);
        return ResponseEntity.ok("## 재입고 알림이 수동 발송되었습니다.");
    }
}
