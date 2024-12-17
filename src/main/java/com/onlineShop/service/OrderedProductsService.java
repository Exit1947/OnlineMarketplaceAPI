package com.onlineShop.service;

import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.OrderedProducts;

import java.util.List;
import java.util.Optional;

public interface OrderedProductsService {

    boolean save(OrderedProducts orderedProducts);

    Optional<OrderedProducts> findById(long id);

    List<OrderedProducts> findAllByProductId(String productId);

    boolean update(OrderedProducts orderedProducts);

    boolean delete(long id);

    boolean deleteAll(List<OrderedProducts> orderedProductsList);

}
