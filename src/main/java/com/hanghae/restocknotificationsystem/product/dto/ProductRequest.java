package com.hanghae.restocknotificationsystem.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private int stockStatus;
}
