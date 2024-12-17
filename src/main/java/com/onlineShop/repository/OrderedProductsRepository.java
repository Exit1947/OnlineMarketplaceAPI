package com.onlineShop.repository;

import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.OrderedProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderedProductsRepository extends JpaRepository<OrderedProducts, Long> {

    Optional<OrderedProducts> findById(long id);

    List<OrderedProducts> findAllByOrderedProductId(String orderedProductId);

}
