package com.webbanhangnongsan.vn.webbanhangnongsan.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long productId;
    String description;
    int discount;
    LocalDateTime enteredDate;
    double price;
    String productImage;
    String productName;
    int quantity;
    boolean status;
    boolean favor;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    Category category;
}
