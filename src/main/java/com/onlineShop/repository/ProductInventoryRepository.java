package com.onlineShop.repository;

import com.onlineShop.models.Product.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

    Optional<ProductInventory> findProductInventoriesByShopIdAndProductId(long shopId, String productId);

    List<ProductInventory> findAllByProductId(String productId);

    List<ProductInventory> findAllByShopId(long productId);

    void deleteAllByProductId(String productId);

}
