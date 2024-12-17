package com.onlineShop.service;

import com.onlineShop.models.Users.EndUserEntities.LikedProduct;

import java.util.List;
import java.util.Optional;

public interface LikedProductService {

    boolean save(LikedProduct likedProduct);

    Optional<LikedProduct> getLikedProductById(String id);

    List<LikedProduct> getAllLikedProductsByUserId(String userId);

    List<LikedProduct> getAllLikedProductsByProductId(String productId);

    boolean update(LikedProduct likedProduct);

    boolean delete(String id);

    boolean deleteAll(List<LikedProduct> likedProducts);

}
