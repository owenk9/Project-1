package com.webbanhangnongsan.vn.webbanhangnongsan.repository;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
//    @Query(value = "select cat.category_name, SUM(od.quantity) AS total_quantity " +
//            "from categories cat " +
//            "join products pro ON cat.category_id = pro.category_id " +
//            "join order_details od ON od.product_id = pro.product_id " +
//            "group by cat.category_name",
//            nativeQuery = true)
//    List<Object[]> getSumQuantityByCategory();
}
