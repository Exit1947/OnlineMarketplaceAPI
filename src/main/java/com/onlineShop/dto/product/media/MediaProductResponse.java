package com.onlineShop.dto.product.media;

import com.onlineShop.dto.media.MediaResponse;
import com.onlineShop.models.Product.Media.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaProductResponse extends MediaResponse {

    private String id;

    @NotBlank(message = "MediaUrl can't be empty")
    private String mediaUrl;

    @NotBlank(message = "Product id can't be empty")
    private String productId;

    @NotNull(message = "Media type can't be empty")
    private MediaType type;

}
