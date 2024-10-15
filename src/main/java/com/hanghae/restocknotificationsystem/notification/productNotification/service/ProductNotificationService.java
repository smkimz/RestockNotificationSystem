package com.hanghae.restocknotificationsystem.notification.productNotification.service;

import com.hanghae.restocknotificationsystem.exceptions.ResourceNotFoundException;
import com.hanghae.restocknotificationsystem.notification.productNotification.domain.NotificationStatus;
import com.hanghae.restocknotificationsystem.notification.productNotification.domain.ProductNotificationHistory;
import com.hanghae.restocknotificationsystem.notification.productNotification.repository.ProductNotificationHistoryRepository;
import com.hanghae.restocknotificationsystem.notification.productUserNotification.domain.ProductUserNotification;
import com.hanghae.restocknotificationsystem.notification.productUserNotification.domain.ProductUserNotificationHistory;
import com.hanghae.restocknotificationsystem.notification.productUserNotification.repository.ProductUserNotificationHistoryRepository;
import com.hanghae.restocknotificationsystem.notification.productUserNotification.repository.ProductUserNotificationRepository;
import com.hanghae.restocknotificationsystem.product.domain.Product;
import com.hanghae.restocknotificationsystem.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductNotificationService {

    private final ProductRepository productRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;

    public ProductNotificationService(ProductRepository productRepository, ProductNotificationHistoryRepository productNotificationHistoryRepository, ProductUserNotificationRepository productUserNotificationRepository, ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository) {
        this.productRepository = productRepository;
        this.productNotificationHistoryRepository = productNotificationHistoryRepository;
        this.productUserNotificationRepository = productUserNotificationRepository;
        this.productUserNotificationHistoryRepository = productUserNotificationHistoryRepository;
    }

    @Transactional
    public void sendRestockNotification(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("## 상품을 찾을 수 없습니다."));

        product.setRestockCount(product.getRestockCount() + 1);
        productRepository.save(product);

        List<ProductUserNotification> users = productUserNotificationRepository.findActiveUsersByProductId(productId);
        sendNotifications(product, users);
    }

    public void manualRestockNotification(Long productId) {
        // manual 하게 발송하는 로직
        ProductNotificationHistory lastHistory = productNotificationHistoryRepository.findLastByProductId(productId);
        List<ProductUserNotification> remainingUsers = productUserNotificationRepository.findRemainingUsers(lastHistory.getLastUserId(), productId);
        sendNotifications(productRepository.findById(productId).get(), remainingUsers);
    }

    private void sendNotifications(Product product, List<ProductUserNotification> users) {
        int userCount = users.size();
        int MAX_NOTIFICATIONS_PER_SECOND = 500;
        int batchSize = Math.min(userCount, MAX_NOTIFICATIONS_PER_SECOND);

        for (int i = 0; i < userCount; i += batchSize) {
            List<ProductUserNotification> batch = users.subList(i, Math.min(i + batchSize, userCount));

            // 알림 발송 로직
            for (ProductUserNotification notification : batch) {
                sendNotificationToUser(product, notification);
            }

            // 재고 체크 후 품절 시 중단
            if (isStockSoldOut(product)) {
                updateNotificationHistory(product, NotificationStatus.CANCELED_BY_SOLD_OUT);
                break;
            }
        }

        updateNotificationHistory(product, NotificationStatus.COMPLETED);
    }

    private void sendNotificationToUser(Product product, ProductUserNotification notification) {
        // 발송 로직
        ProductUserNotificationHistory history = new ProductUserNotificationHistory();
        history.setProduct(product);
        history.setUserId(notification.getUserId());
        history.setRestockCount(product.getRestockCount());
        history.setNotifiedDate(LocalDateTime.now());
        productUserNotificationHistoryRepository.save(history);
    }

    private boolean isStockSoldOut(Product product) {
        // 상품 재고 상태 확인 로직
        return product.getStockStatus() == 0;
    }

    private void updateNotificationHistory(Product product, NotificationStatus status) {
        ProductNotificationHistory history = new ProductNotificationHistory();
        history.setProduct(product);
        history.setRestockCount(product.getRestockCount());
        history.setStatus(status);
        productNotificationHistoryRepository.save(history);
    }
}
