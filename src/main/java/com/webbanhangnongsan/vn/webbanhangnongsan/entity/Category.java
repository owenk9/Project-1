package com.webbanhangnongsan.vn.webbanhangnongsan.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long categoryId;
    String categoryImage;
    String categoryName;
}
