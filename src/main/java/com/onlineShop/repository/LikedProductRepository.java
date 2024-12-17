package com.onlineShop.repository;

import com.onlineShop.models.Users.EndUserEntities.LikedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedProductRepository extends JpaRepository<LikedProduct, String> {

    boolean existsByUserIdAndProductId(String userId, String productId);

    List<LikedProduct> findAllByUserId(String userId);

    List<LikedProduct> findAllByProductId(String productId);

}
