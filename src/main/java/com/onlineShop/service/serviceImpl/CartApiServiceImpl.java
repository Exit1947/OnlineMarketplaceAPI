package com.onlineShop.service.serviceImpl;

import com.onlineShop.converter.cart.CartConverter;
import com.onlineShop.dto.cart.CartRequest;
import com.onlineShop.dto.cart.CartResponse;
import com.onlineShop.dto.cart.productDto.ProductCartRequest;
import com.onlineShop.models.Product.Product;
import com.onlineShop.models.Users.EndUser;
import com.onlineShop.models.Users.EndUserEntities.cart.Cart;
import com.onlineShop.models.Users.EndUserEntities.cart.Item;
import com.onlineShop.repository.EndUserRepository;
import com.onlineShop.repository.ProductRepository;
import com.onlineShop.service.CartApiService;
import com.onlineShop.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartApiServiceImpl implements CartApiService {

    private final CartService cartService;
    private final EndUserRepository endUserRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartApiServiceImpl(CartService cartService, EndUserRepository endUserRepository, ProductRepository productRepository) {
        this.cartService = cartService;
        this.endUserRepository = endUserRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<CartResponse> getCartForEndUser(String endUserId) {
        Optional<Cart> cart = cartService.getByUserId(endUserId);
        return cart.map(value -> new ResponseEntity<>(CartConverter.toCartResponse(value), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> addCartItemForEndUser(String userId, ProductCartRequest productCartRequest) {
        if(endUserRepository.existsById(userId)) {
            Optional<Cart> existingCart = cartService.getByUserId(userId);
            if(existingCart.isPresent()) {
                Cart cart = existingCart.get();

                Optional<Item> existingItem = cartService.existItemInCart(cart, productCartRequest.getProductId());
                if (existingItem.isPresent()) {
                    Item item = existingItem.get();
                    item.setCountOfProducts(item.getCountOfProducts() + productCartRequest.getQuantity());

                    cartService.save(cart);

                    return new ResponseEntity<>(HttpStatus.OK);
                }

                Item item = createItem(cart, productCartRequest);
                if(item != null){
                    cartService.saveItem(item);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            else {
                Cart cart = new Cart();
                cart.setEndUser(endUserRepository.findById(userId).get());
                if(productCartRequest.getQuantity() > 0) {
                    Item item = createItem(cart, productCartRequest);
                    if (item != null) {
                        cart.getItems().add(item);
                        cartService.save(cart);
                        return new ResponseEntity<>(HttpStatus.CREATED);
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateCartItemByEndUserId(String endUserId, ProductCartRequest productCartRequest){
        Optional<Cart> existingCart = cartService.getByUserId(endUserId);
        if(existingCart.isPresent()) {
            Cart cart = existingCart.get();
            Optional<Item> existingItem = cartService.existItemInCart(cart, productCartRequest.getProductId());
            if(existingItem.isPresent()) {
                Item item = existingItem.get();
                if(productCartRequest.getQuantity() > 0) {
                    item.setCountOfProducts(productCartRequest.getQuantity());
                    cartService.saveItem(item);

                    return new ResponseEntity<>(HttpStatus.OK);
                }
                cart.getItems().remove(existingItem.get());
                cartService.save(cart);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> removeCartItemByUserId(String endUserId, ProductCartRequest productCartRequest) {
        Optional<Cart> existingCart = cartService.getByUserId(endUserId);
        if(existingCart.isPresent()) {
            Cart cart = existingCart.get();
            Optional<Item> existingItem = cartService.existItemInCart(cart, productCartRequest.getProductId());

            if (existingItem.isPresent()) {
                cart.getItems().remove(existingItem.get());
                cartService.save(cart);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> addCartItem(String cartId, ProductCartRequest productCartRequest) {
        Optional<Cart> existingCart = cartService.getById(cartId);
        if(existingCart.isPresent()) {
            Cart cart = existingCart.get();

            Optional<Item> existingItem = cartService.existItemInCart(cart, productCartRequest.getProductId());
            if (existingItem.isPresent()) {
                Item item = existingItem.get();
                item.setCountOfProducts(item.getCountOfProducts() + productCartRequest.getQuantity());

                cartService.save(cart);

                return new ResponseEntity<>(HttpStatus.OK);
            }

            Item item = createItem(cart, productCartRequest);
            if(item != null){
                cartService.saveItem(item);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateCartItem(String cartId, ProductCartRequest productCartRequest) {
        Optional<Cart> existingCart = cartService.getById(cartId);
        if(existingCart.isPresent()) {
            Cart cart = existingCart.get();
            Optional<Item> existingItem = cartService.existItemInCart(cart, productCartRequest.getProductId());
            if(existingItem.isPresent()) {
                Item item = existingItem.get();
                if(productCartRequest.getQuantity() > 0) {
                    item.setCountOfProducts(productCartRequest.getQuantity());
                    cartService.saveItem(item);

                    return new ResponseEntity<>(HttpStatus.OK);
                }
                cart.getItems().remove(existingItem.get());
                cartService.save(cart);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> removeCartItem(String cartId, ProductCartRequest productCartRequest) {
        Optional<Cart> existingCart = cartService.getById(cartId);
        if(existingCart.isPresent()) {
            Cart cart = existingCart.get();
            Optional<Item> existingItem = cartService.existItemInCart(cart, productCartRequest.getProductId());

            if (existingItem.isPresent()) {
                cart.getItems().remove(existingItem.get());
                cartService.save(cart);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<CartResponse> getCart(String cartId) {
        Optional<Cart> existingCart = cartService.getById(cartId);
        return existingCart.map(cart -> new ResponseEntity<>(CartConverter.toCartResponse(cart), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> removeCart(String cartId) {
        Optional<Cart> existingCart = cartService.getById(cartId);
        if(existingCart.isPresent()) {
            cartService.deleteCartById(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Item createItem(Cart cart, ProductCartRequest productCartRequest) {
        Optional<Product> existingProduct = productRepository.findById(productCartRequest.getProductId());
        if (existingProduct.isPresent()) {
            Item item = CartConverter.toItem(productCartRequest);
            item.setCart(cart);
            item.setProduct(existingProduct.get());

            return item;
        }
        return null;
    }

}
