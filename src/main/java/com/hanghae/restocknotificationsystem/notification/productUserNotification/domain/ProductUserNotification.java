package com.hanghae.restocknotificationsystem.notification.productUserNotification.domain;

import com.hanghae.restocknotificationsystem.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductUserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long userId;

    private boolean isActive;  // 활성화 여부

    private LocalDateTime createdDate;  // 생성 날짜
    private LocalDateTime updatedDate;  // 수정 날짜
}
