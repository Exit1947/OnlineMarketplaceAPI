package com.onlineShop.dto.cart;

import com.onlineShop.dto.cart.productDto.ProductCartRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    private String id;

    private String endUserId;

    private List<ProductCartRequest> productList;

}
