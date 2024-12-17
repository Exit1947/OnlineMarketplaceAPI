package com.onlineShop.repository;

import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.ShoppingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, String> {

    List<ShoppingOrder> getAllByEndUserId(String userId);

    boolean existsById(String id);

}
