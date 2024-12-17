package com.onlineShop.service;

import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.ShoppingOrder;

import java.util.List;
import java.util.Optional;

public interface ShoppingOrderService {

    void save(ShoppingOrder shoppingOrder);

    Optional<ShoppingOrder> getById(String id);

    List<ShoppingOrder> getAllByUserId(String id);

    void delete(String id);

    void deleteAll(List<ShoppingOrder> shoppingOrders);

    boolean exists(String id);

}
