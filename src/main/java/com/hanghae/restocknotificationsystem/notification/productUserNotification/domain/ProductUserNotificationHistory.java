package com.hanghae.restocknotificationsystem.notification.productUserNotification.domain;

import com.hanghae.restocknotificationsystem.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductUserNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long userId;

    private int restockCount;

    private LocalDateTime notifiedDate;
}
