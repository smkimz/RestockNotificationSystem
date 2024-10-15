package com.hanghae.restocknotificationsystem.notification.productUserNotification.controller;

import com.hanghae.restocknotificationsystem.notification.productUserNotification.service.ProductUserNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/{productId}/notifications")
public class ProductUserNotificationController {

    private final ProductUserNotificationService productUserNotificationService;

    public ProductUserNotificationController(ProductUserNotificationService productUserNotificationService) {
        this.productUserNotificationService = productUserNotificationService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToRestockNotification(@PathVariable Long productId, @RequestParam Long userId) {
        productUserNotificationService.subscribe(productId, userId);
        return ResponseEntity.ok("## 유저의 상품 재입고 알림 발송 구독이 완료되었습니다.");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeFromRestockNotification(@PathVariable Long productId, @RequestParam Long userId) {
        productUserNotificationService.unsubscribe(productId, userId);
        return ResponseEntity.ok("## 유저의 상품 재입고 알림 발송 구독 해제가 완료되었습니다.");
    }
}
