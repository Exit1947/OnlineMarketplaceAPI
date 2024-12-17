package com.onlineShop.service;

import com.onlineShop.dto.product.ProductCardInfoResponse;
import com.onlineShop.models.Product.DiscountProduct;
import com.onlineShop.models.Product.Media.Media;
import com.onlineShop.models.Product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void saveProduct(Product product);

    Optional<Product> getById(String id);

    Optional<Product> getByTitle(String title);

    List<Product> getRandomProductCardsById(int count);

    boolean deleteById(String id);

    Optional<DiscountProduct> getDiscountByProductId(String productId);

    void saveDiscount(DiscountProduct discount);

    void deleteDiscount(long discountId);

    String thumbnailImageUrl(String thumbnailImage);

    List<Media> getAllForEntityById(String productId);

    boolean existByTitle(String title);


}
