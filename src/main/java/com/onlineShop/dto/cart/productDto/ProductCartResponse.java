package com.onlineShop.dto.cart.productDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartResponse {

    private String productId;

    private String title;

    private boolean available;

    private String thumbnailImageUrl;

    private int quantity;

    private double price;

}
