package com.onlineShop.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class ProductCardInfoResponse {

    private String id;

    @NotBlank(message = "Title of product can't be empty")
    @Length(min = 3, max = 400, message = "Product title must be between 3 and 100 characters")
    private String title;

    private int discount;

    private int countOfFeedbacks;

    @Positive(message = "Price must be a positive number")
    private double price;

    private String thumbnailImage;

}
