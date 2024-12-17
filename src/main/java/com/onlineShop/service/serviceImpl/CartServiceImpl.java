package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Users.EndUserEntities.cart.Cart;
import com.onlineShop.models.Users.EndUserEntities.cart.Item;
import com.onlineShop.repository.CartRepository;
import com.onlineShop.repository.ItemRepository;
import com.onlineShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void save(final Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getById(String id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Cart> getByUserId(String userId) {
        return cartRepository.getCartByEndUserId(userId);
    }

    @Override
    public Optional<Item> existItemInCart(Cart cart, String productId) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    @Override
    public void deleteCartById(String id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            itemRepository.deleteAll(cart.getItems());

            cartRepository.delete(cart);
        }
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void deleteItemById(long itemId) {
        itemRepository.deleteById(itemId);
    }


    @Override
    public boolean existsByUserId(String userId) {
        return cartRepository.existsByEndUserId(userId);
    }

}
