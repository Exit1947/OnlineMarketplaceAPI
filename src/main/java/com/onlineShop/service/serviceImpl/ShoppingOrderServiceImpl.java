package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.ShoppingOrder;
import com.onlineShop.repository.ShoppingOrderRepository;
import com.onlineShop.service.OrderedProductsService;
import com.onlineShop.service.ShoppingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {

    private final OrderedProductsService orderedProductsService;
    private final ShoppingOrderRepository shoppingOrderRepository;

    @Autowired
    public ShoppingOrderServiceImpl(OrderedProductsService orderedProductsService, ShoppingOrderRepository shoppingOrderRepository) {
        this.orderedProductsService = orderedProductsService;
        this.shoppingOrderRepository = shoppingOrderRepository;
    }

    @Override
    public void save(ShoppingOrder shoppingOrder) {
        shoppingOrderRepository.save(shoppingOrder);
    }

    @Override
    public Optional<ShoppingOrder> getById(String id) {
        return shoppingOrderRepository.findById(id);
    }

    @Override
    public List<ShoppingOrder> getAllByUserId(String userId) {
        return shoppingOrderRepository.getAllByEndUserId(userId);
    }

    @Override
    public void delete(String id) {
        Optional<ShoppingOrder> existingShoppingOrder = shoppingOrderRepository.findById(id);
        if(existingShoppingOrder.isPresent()) {
            ShoppingOrder shoppingOrder = existingShoppingOrder.get();
            orderedProductsService.deleteAll(shoppingOrder.getOrderedProductsList());
            shoppingOrderRepository.delete(shoppingOrder);
        }
    }

    @Override
    public void deleteAll(List<ShoppingOrder> shoppingOrders) {
        shoppingOrders.
                forEach(shoppingOrder->delete(shoppingOrder.getId()));
    }

    @Override
    public boolean exists(String id) {
        return shoppingOrderRepository.existsById(id);
    }
}
