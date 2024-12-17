package com.onlineShop.repository;

import com.onlineShop.models.Users.EndUserEntities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> getCartById(String id);

    Optional<Cart> getCartByEndUserId(String userId);

    void deleteById(String id);

    boolean existsByEndUserId(String userId);

}
