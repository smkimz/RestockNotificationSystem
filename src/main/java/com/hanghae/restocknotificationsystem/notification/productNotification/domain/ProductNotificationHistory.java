package com.hanghae.restocknotificationsystem.notification.productNotification.domain;

import com.hanghae.restocknotificationsystem.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int restockCount;

    @Enumerated(EnumType.STRING)
    // IN_PROGRESS, CANCELED_BY_SOLD_OUT, CANCELED_BY_ERROR, COMPLETED
    private NotificationStatus status;

    private Long lastUserId;
}