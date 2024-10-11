package com.webbanhangnongsan.vn.webbanhangnongsan.repository;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
