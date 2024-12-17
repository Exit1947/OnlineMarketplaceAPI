package com.onlineShop.service.serviceImpl;

import com.onlineShop.models.Product.DiscountProduct;
import com.onlineShop.models.Product.Media.Media;
import com.onlineShop.models.Product.Product;
import com.onlineShop.models.Users.EndUserEntities.LikedProduct;
import com.onlineShop.models.Users.EndUserEntities.shoppingOrder.OrderedProducts;
import com.onlineShop.repository.ProductRepository;
import com.onlineShop.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MediaProductServiceImpl mediaProductService;
    private final DiscountProductService discountProductService;
    private final OrderedProductsService orderedProductsService;
    private final LikedProductService likedProductService;
    private final ProductInventoryService productInventoryService;
    private final FeedbackService feedbackService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MediaProductServiceImpl mediaProductService, DiscountProductService discountProductService,
                              OrderedProductsService orderedProductsService, LikedProductService likedProductService, ProductInventoryService productInventoryService,
                              FeedbackService feedbackService) {
        this.productRepository = productRepository;
        this.mediaProductService = mediaProductService;
        this.discountProductService = discountProductService;
        this.orderedProductsService = orderedProductsService;
        this.likedProductService = likedProductService;
        this.productInventoryService = productInventoryService;
        this.feedbackService = feedbackService;
    }

    @Override
    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Optional<Product> getById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> getByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    @Override
    public List<Product> getRandomProductCardsById(int count) {
        return productRepository.findRandomProduct(count);
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isPresent()){
            Product product = existingProduct.get();

            deleteFromDiscountProductIfExist(id);
            deleteFromOrderedProductsIfExist(id);
            deleteFromLikedProductIfExist(id);
            deleteFromProductInventoryIfExist(id);
            deleteFromFeedbackIfExist(id);

            if(product.getThumbnailImage()!=null) {
                mediaProductService.deleteMediaFile(product.getThumbnailImage());
            }

            var testList = mediaProductService.getAllForEntity(product.getId());
            mediaProductService.deleteAll(testList);

            productRepository.deleteById(id);

            return true;
        }
        return false;
    }

    @Override
    public Optional<DiscountProduct> getDiscountByProductId(String productId) {
        return discountProductService.findByProductId(productId);
    }

    @Override
    public void saveDiscount(DiscountProduct discount) {
        discountProductService.save(discount);
    }

    @Override
    public void deleteDiscount(long discountId) {
        discountProductService.delete(discountId);
    }

    @Override
    public boolean existByTitle(String title) {
        return productRepository.existsByTitle(title);
    }

    @Override
    public String thumbnailImageUrl(String thumbnailImage){
        if(thumbnailImage!=null && !thumbnailImage.isEmpty()) {
            return mediaProductService.getUrl(thumbnailImage);
        }
        return null;
    }

    @Override
    public List<Media> getAllForEntityById(String productId){
        List<Media> mediaList = mediaProductService.getAllForEntity(productId);
        mediaList
                .forEach(media -> media.setMediaName(mediaProductService.getUrl(media.getMediaName())));
        return mediaList;
    }

    private void deleteFromDiscountProductIfExist(String productId){
        Optional<DiscountProduct> discount = discountProductService.findByProductId(productId);
        discount.ifPresent(discountProduct ->
                discountProductService.delete(discountProduct.getId()));
    }

    private void deleteFromFeedbackIfExist(String productId){
        feedbackService.deleteAllForProduct(productId);
    }

    private void deleteFromOrderedProductsIfExist(String productId){
        List<OrderedProducts> orderedProducts = orderedProductsService.findAllByProductId(productId);
        if(orderedProducts!=null && !orderedProducts.isEmpty()) {
            orderedProductsService.deleteAll(orderedProducts);
        }
    }

    private void deleteFromLikedProductIfExist(String productId){
        List<LikedProduct> likedProducts = likedProductService.getAllLikedProductsByProductId(productId);
        if(likedProducts!=null && !likedProducts.isEmpty()) {
            likedProductService.deleteAll(likedProducts);
        }
    }

    private void deleteFromProductInventoryIfExist(String productId){
        productInventoryService.deleteAllProductInventoryByProductId(productId);
    }

}
