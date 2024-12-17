package com.onlineShop.dto.cart;

import com.onlineShop.dto.cart.productDto.ProductCartResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private String id;

    private double totalPrice;

    private List<ProductCartResponse> productList;

}
