package com.webbanhangnongsan.vn.webbanhangnongsan.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "order_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderDetailId;
    double price;
    int quantity;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
