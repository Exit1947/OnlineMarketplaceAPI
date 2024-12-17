package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Product.ProductInventory;
import com.onlineShop.repository.ProductInventoryRepository;
import com.onlineShop.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

    @Autowired
    public ProductInventoryServiceImpl(ProductInventoryRepository productInventoryRepository) {
        this.productInventoryRepository = productInventoryRepository;
    }


    @Override
    public boolean save(ProductInventory productInventory) {
        Optional<ProductInventory> existingProductInventory = getProductInventoryByShopIdAndProductId(productInventory.getShop().getId(), productInventory.getProduct().getId());
        if(existingProductInventory.isEmpty()){
            productInventoryRepository.save(productInventory);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ProductInventory> getProductInventoryByShopIdAndProductId(long shopId, String productId) {
        return productInventoryRepository.findProductInventoriesByShopIdAndProductId(shopId, productId);
    }

    @Override
    public List<ProductInventory> getAllProductInventoryForShop(long shopId) {
        return productInventoryRepository.findAllByShopId(shopId);
    }

    @Override
    public List<ProductInventory> getAllProductInventoryForProduct(String productId) {
        return productInventoryRepository.findAllByProductId(productId);
    }

    @Override
    public boolean deleteAllProductInventoryByProductId(String productId) {
        productInventoryRepository.deleteAllByProductId(productId);
        return true;
    }

    @Override
    public boolean deleteById(long id) {
        if(productInventoryRepository.existsById(id)){
            productInventoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
