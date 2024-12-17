package com.onlineShop.service;

import com.onlineShop.models.Users.EndUserEntities.cart.Cart;
import com.onlineShop.models.Users.EndUserEntities.cart.Item;

import java.util.Optional;

public interface CartService {

    void save(Cart cart);

    Optional<Cart> getById(String id);

    Optional<Cart> getByUserId(String id);

    Optional<Item> existItemInCart(Cart cart, String productId);

    void deleteCartById(String id);

    void saveItem(Item item);

    void deleteItemById(long itemId);

    boolean existsByUserId(String id);

}
