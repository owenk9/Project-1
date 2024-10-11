package com.webbanhangnongsan.vn.webbanhangnongsan.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "favorites")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long favoriteId;
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
    @ManyToOne()
    @JoinColumn(name = "productId")
    Product product;
}
