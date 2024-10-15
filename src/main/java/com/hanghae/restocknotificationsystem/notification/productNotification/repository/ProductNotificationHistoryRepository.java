package com.hanghae.restocknotificationsystem.notification.productNotification.repository;

import com.hanghae.restocknotificationsystem.notification.productNotification.domain.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {
    ProductNotificationHistory findLastByProductId(Long productId);
}
