package com.onlineShop.service;

import com.onlineShop.models.Product.ProductInventory;

import java.util.List;
import java.util.Optional;

public interface ProductInventoryService {

    boolean save(ProductInventory productInventory);

    Optional<ProductInventory> getProductInventoryByShopIdAndProductId(long shopId, String productId);

    List<ProductInventory> getAllProductInventoryForShop(long shopId);

    List<ProductInventory> getAllProductInventoryForProduct(String productId);

    boolean deleteAllProductInventoryByProductId(String productId);

    boolean deleteById(long id);

}
