package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.OrderedProducts;
import com.onlineShop.repository.OrderedProductsRepository;
import com.onlineShop.service.OrderedProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderedProductsServiceImpl implements OrderedProductsService {

    private final OrderedProductsRepository orderedProductsRepository;

    @Autowired
    public OrderedProductsServiceImpl(OrderedProductsRepository orderedProductsRepository) {
        this.orderedProductsRepository = orderedProductsRepository;
    }

    @Override
    public boolean save(OrderedProducts orderedProducts) {
        orderedProductsRepository.save(orderedProducts);
        return true;
    }

    @Override
    public Optional<OrderedProducts> findById(long id) {
        return orderedProductsRepository.findById(id);
    }

    @Override
    public List<OrderedProducts> findAllByProductId(String productId) {
        return orderedProductsRepository.findAllByOrderedProductId(productId);
    }

    @Override
    public boolean update(OrderedProducts orderedProducts) {
        Optional<OrderedProducts> existingOrderedProducts = orderedProductsRepository.findById(orderedProducts.getId());
        existingOrderedProducts.ifPresent(products -> orderedProducts.setId(products.getId()));
        orderedProductsRepository.save(orderedProducts);
        return true;
    }

    @Override
    public boolean delete(long id) {
        if(orderedProductsRepository.existsById(id)) {
            orderedProductsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAll(List<OrderedProducts> orderedProductsList) {
        orderedProductsList.
                forEach(orderedProduct -> delete(orderedProduct.getId()));
        return true;
    }

}
