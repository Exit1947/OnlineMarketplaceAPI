package com.onlineShop.repository;

import com.onlineShop.models.Product.DiscountProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProduct, Long> {

    Optional<DiscountProduct> findById(long id);

    Optional<DiscountProduct> findByProductId(String productId);

}
