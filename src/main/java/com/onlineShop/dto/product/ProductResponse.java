package com.onlineShop.dto.product;

import com.onlineShop.dto.product.media.MediaProductResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductResponse {

    private String id;

    @NotBlank(message = "Title of product can't be empty")
    @Length(min = 3, max = 400, message = "Product title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Category of product can't be empty")
    @Length(min = 3, max = 150, message = "Product category must be between 3 and 100 characters")
    private String category;

    @Positive(message = "Price must be a positive number")
    private double price;

    @PositiveOrZero(message = "Discount must be a positive number or zero.")
    private int discount;

    @Length(min = 10, max = 500, message = "Product description must be between 10 and 500 characters")
    private String description;

    private List<CharacteristicDto> characteristicValuesList;

    private List<MediaProductResponse> mediaList;

}
