package com.webbanhangnongsan.vn.webbanhangnongsan.repository;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
