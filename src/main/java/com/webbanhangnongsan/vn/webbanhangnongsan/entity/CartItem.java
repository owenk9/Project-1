package com.webbanhangnongsan.vn.webbanhangnongsan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    private Long id;
    private String name;
    private double unitPrice;
    private int quantity;
    private double totalPrice;
    private Product product;
}
