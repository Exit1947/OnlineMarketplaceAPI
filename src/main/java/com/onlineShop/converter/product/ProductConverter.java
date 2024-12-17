package com.onlineShop.converter.product;

import com.onlineShop.converter.product.media.MediaConverter;
import com.onlineShop.models.Product.Characteristic.ProductCharacteristic;
import com.onlineShop.dto.product.CharacteristicDto;
import com.onlineShop.dto.product.ProductCardInfoResponse;
import com.onlineShop.dto.product.ProductRequest;
import com.onlineShop.dto.product.ProductResponse;
import com.onlineShop.models.Product.DiscountProduct;
import com.onlineShop.models.Product.Media.Media;
import com.onlineShop.models.Product.Product;

import java.util.Comparator;
import java.util.List;

public class ProductConverter {

    public static Product toProduct(final ProductRequest request){
        return Product.builder()
                .id(request.getId())
                .nameCategory(request.getNameCategory())
                .description(request.getDescription())
                .title(request.getTitle())
                .price(request.getPrice())
                .discount(request.getDiscount() > 0)
                .build();
    }

    public static ProductResponse toProductResponse(final Product product, int discount, List<Media> mediaList){
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .category(product.getNameCategory())
                .price(product.getPrice())
                .description(product.getDescription())
                .characteristicValuesList(product.getCharacteristicValues().stream()
                    .sorted(Comparator.comparingInt(ProductCharacteristic::getNumber))
                    .map(ProductConverter::toCharacteristicDto).toList())
                .mediaList(mediaList.stream()
                     .map(MediaConverter::toMediaProductResponse)
                     .toList())
                .discount(discount)
                .build();
    }

    public static ProductCardInfoResponse toProductCardResponse(final Product product, int discount, int feedbacksCount, String thumbnailImageUrl){
        return ProductCardInfoResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .discount(discount)
                .thumbnailImage(thumbnailImageUrl)
                .countOfFeedbacks(feedbacksCount)
                .build();
    }

    public static CharacteristicDto toCharacteristicDto(ProductCharacteristic productCharacteristic){
        return CharacteristicDto.builder()
                .id(productCharacteristic.getId())
                .name(productCharacteristic.getCharacteristic().getName())
                .value(productCharacteristic.getValue())
                .description(productCharacteristic.getCharacteristic().getDescription())
                .characteristicId(productCharacteristic.getCharacteristic().getId())
                .build();
    }

    public static DiscountProduct toDiscountProduct(Product product, ProductRequest productRequest){
        return DiscountProduct.builder()
                .product(product)
                .discount(productRequest.getDiscount())
                .dateFrom(productRequest.getDateFrom())
                .dateTo(productRequest.getDateTo())
                .build();
    }

}
