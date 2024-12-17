package com.onlineShop.dto.media;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaRequest {

    @NotBlank(message = "MediaUrl can't be empty")
    private String mediaUrl;

}
