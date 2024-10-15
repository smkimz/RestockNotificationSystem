package com.hanghae.restocknotificationsystem.notification.productUserNotification.repository;

import com.hanghae.restocknotificationsystem.notification.productUserNotification.domain.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {

    @Query("SELECT p FROM ProductUserNotification p WHERE p.product.id = :productId AND p.userId > :lastUserId AND p.isActive = true ORDER BY p.userId ASC")
    List<ProductUserNotification> findRemainingUsers(@Param("lastUserId") Long lastUserId, @Param("productId") Long productId);

    @Query("SELECT p FROM ProductUserNotification p WHERE p.product.id = :productId AND p.isActive = true ORDER BY p.userId ASC")
    List<ProductUserNotification> findActiveUsersByProductId(@Param("productId") Long productId);

    @Query("SELECT p FROM ProductUserNotification p WHERE p.product.id = :productId AND p.userId = :userId")
    Optional<ProductUserNotification> findByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") Long userId);
}


