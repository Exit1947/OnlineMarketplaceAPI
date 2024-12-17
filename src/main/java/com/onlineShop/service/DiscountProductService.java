package com.onlineShop.service;

import com.onlineShop.models.Product.DiscountProduct;

import java.util.List;
import java.util.Optional;

public interface DiscountProductService {

    boolean save(DiscountProduct discountProduct);

    Optional<DiscountProduct> findById(long id);

    Optional<DiscountProduct> findByProductId(String productId);

    boolean update(DiscountProduct discountProduct);

    boolean delete(long id);

    boolean deleteAll(List<DiscountProduct> productList);

}
