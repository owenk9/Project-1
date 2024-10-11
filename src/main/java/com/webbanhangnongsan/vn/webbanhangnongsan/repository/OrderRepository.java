package com.webbanhangnongsan.vn.webbanhangnongsan.repository;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "select * from orders where user_id = ?1", nativeQuery = true)
    List<Order> findOrderByUserId(Long id);
}
