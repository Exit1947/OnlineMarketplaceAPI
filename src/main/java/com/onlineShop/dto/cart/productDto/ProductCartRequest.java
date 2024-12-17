package com.onlineShop.dto.cart.productDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartRequest {

    @NotBlank(message = "Product id can't be blank.")
    private String productId;

    @PositiveOrZero(message = "Quantity must be more than zero.")
    private int quantity;

}
