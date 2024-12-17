package com.onlineShop.service;

import com.onlineShop.dto.cart.CartRequest;
import com.onlineShop.dto.cart.CartResponse;
import com.onlineShop.dto.cart.productDto.ProductCartRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CartApiService {

    ResponseEntity<CartResponse> getCartForEndUser(String endUserId);

    ResponseEntity<HttpStatus> addCartItemForEndUser(String endUserId, ProductCartRequest productCartRequest);

    ResponseEntity<HttpStatus> updateCartItemByEndUserId(String endUserId, ProductCartRequest productCartRequest);

    ResponseEntity<HttpStatus> removeCartItemByUserId(String endUserId, ProductCartRequest productCartRequest);

    ResponseEntity<HttpStatus> addCartItem(String cartId, ProductCartRequest productCartRequest);

    ResponseEntity<HttpStatus> updateCartItem(String cartId, ProductCartRequest productCartRequest);

    ResponseEntity<HttpStatus> removeCartItem(String cartId, ProductCartRequest productCartRequest);

    ResponseEntity<CartResponse> getCart(String cartId);

    ResponseEntity<HttpStatus> removeCart(String cartId);

}
