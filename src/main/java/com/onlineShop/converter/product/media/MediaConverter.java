package com.onlineShop.converter.product.media;

import com.onlineShop.dto.product.media.MediaProductRequest;
import com.onlineShop.dto.product.media.MediaProductResponse;
import com.onlineShop.models.Product.Media.Media;

public class MediaConverter {

    public static Media toMedia(MediaProductRequest mediaProductRequest) {
        return Media.builder()
                .id(mediaProductRequest.getId())
                .mediaName(
                        mediaProductRequest.getMediaUrl().substring(mediaProductRequest.getMediaUrl().lastIndexOf("/")+1))
                .type(mediaProductRequest.getType())
                .build();
    }

    public static MediaProductResponse toMediaProductResponse(Media media) {
        return MediaProductResponse.builder()
                .id(media.getId())
                .mediaUrl(media.getMediaName())
                .productId(media.getProduct().getId())
                .type(media.getType())
                .build();
    }

}
