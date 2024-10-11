package com.webbanhangnongsan.vn.webbanhangnongsan.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long orderId;
    String address;
    double amount;
    LocalDateTime orderDate;
    String phone;
    int status;
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
