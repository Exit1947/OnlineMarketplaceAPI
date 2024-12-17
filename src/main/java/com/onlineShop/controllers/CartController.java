package com.onlineShop.controllers;

import com.onlineShop.dto.cart.CartRequest;
import com.onlineShop.dto.cart.CartResponse;
import com.onlineShop.dto.cart.productDto.ProductCartRequest;
import com.onlineShop.security.JwtDecoder;
import com.onlineShop.security.JwtToPrincipalConverter;
import com.onlineShop.service.CartApiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartApiService cartApiService;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public CartController(CartApiService cartApiService, JwtDecoder jwtDecoder) {
        this.cartApiService = cartApiService;
        this.jwtDecoder = jwtDecoder;
    }

    @GetMapping("/me")
    public ResponseEntity<CartResponse> getSelfCartForEndUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestEndUserId;
        try {
            requestEndUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return cartApiService.getCartForEndUser(requestEndUserId);
    }

    @PostMapping("/item/me")
    public ResponseEntity<HttpStatus> addCartItemForEndUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody @Valid ProductCartRequest productCartRequest){
        String requestEndUserId;
        try {
            requestEndUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return cartApiService.addCartItemForEndUser(requestEndUserId, productCartRequest);
    }

    @PutMapping("/item/me")
    public ResponseEntity<HttpStatus> updateSelfCartItemForEndUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody @Valid ProductCartRequest productCartRequest){
        String requestEndUserId;
        try {
            requestEndUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return cartApiService.updateCartItemByEndUserId(requestEndUserId, productCartRequest);
    }

    @DeleteMapping("/item/me")
    public ResponseEntity<HttpStatus> removeSelfCartItemForEndUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody @Valid ProductCartRequest productCartRequest) {
        String requestEndUserId;
        try {
            requestEndUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return cartApiService.removeCartItemByUserId(requestEndUserId, productCartRequest);
    }

    @GetMapping("/end-user-id={endUserId}")
    public ResponseEntity<CartResponse> getCartForEndUser(@PathVariable("endUserId") String endUserId) {
        if(endUserId != null && !endUserId.isEmpty()){
            return cartApiService.getCartForEndUser(endUserId);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/item/cart-id={cartId}")
    public ResponseEntity<HttpStatus> addCartItem(@PathVariable("cartId") String cartId, @RequestBody @Valid ProductCartRequest productCartRequest) {
        if(cartId != null && !cartId.isEmpty()){
            return cartApiService.addCartItem(cartId, productCartRequest);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/item/cart-id={cartId}")
    public ResponseEntity<HttpStatus> updateCartItem(@PathVariable("cartId") String cartId, @RequestBody @Valid ProductCartRequest productCartRequest){
        if(cartId != null && !cartId.isEmpty()){
            return cartApiService.updateCartItem(cartId, productCartRequest);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/item/cart-id={cartId}")
    public ResponseEntity<HttpStatus> removeCartItem(@PathVariable("cartId") String cartId, @RequestBody @Valid ProductCartRequest productCartRequest){
        if(cartId != null && !cartId.isEmpty()){
            return cartApiService.removeCartItem(cartId, productCartRequest);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cart-id={cartId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable("cartId") String cartId){
        if(cartId != null && !cartId.isEmpty()){
            return cartApiService.getCart(cartId);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cart-id={cartId}")
    public ResponseEntity<HttpStatus> removeCart(@PathVariable("cartId") String cartId){
        if(cartId != null && !cartId.isEmpty()){
            return cartApiService.removeCart(cartId);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
