package com.webbanhangnongsan.vn.webbanhangnongsan.repository;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    @Query(value = "SELECT Count(favorite_id)  FROM favorites  where user_id = ?;", nativeQuery = true)
    public Integer selectCountSave(Long userId);
}
