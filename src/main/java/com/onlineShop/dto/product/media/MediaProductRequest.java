package com.onlineShop.dto.product.media;

import com.onlineShop.dto.media.MediaRequest;
import com.onlineShop.dto.product.ProductRequest;
import com.onlineShop.models.Product.Media.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaProductRequest extends MediaRequest {

    private String id;

    @NotNull(message = "Media type can't be empty")
    private MediaType type;

}
